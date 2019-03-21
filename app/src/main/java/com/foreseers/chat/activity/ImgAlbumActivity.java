package com.foreseers.chat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.dialog.DelImgDialog;
import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImgAlbumActivity extends BaseActivity {
    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    private ViewPager vp;
    private List<ImageView> imageViews = new ArrayList<ImageView>();//显示图片的ImageView
    private int position;//从上个页面获取的子项position
    private List<String> urls = new ArrayList<String>();//上个页面获取的URL列表
    //    private DisplayImageOptions options;
    private MyViewPagerAdapter adapter = new MyViewPagerAdapter();
    private LinearLayout vp_ll;
    private Map<Integer, float[]> xyMap = new HashMap<Integer, float[]>();//接收所有图片的坐标
    private float pivotX, pivotY;//放大缩小的中心点
    private TextView vp_text;
    private Context mContext;
    private DelImgDialog delImgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_img_album);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        xyMap = (HashMap<Integer, float[]>) getIntent().getExtras().get("xyMap");
        position = getIntent().getIntExtra("position", 0);
        //得到放缩中心点
        pivotX = xyMap.get(position)[0];
        pivotY = xyMap.get(position)[1];
        //放大动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, pivotX, pivotY);
        //设置动画从0放大到正常大小
        scaleAnimation.setDuration(350);//设置动画时长
        scaleAnimation.setFillAfter(true);
        vp_ll.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                initData();//动画完成后加载数据
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position1) {//左右滑动时更新中心点和文字信息
//                pivotX=xyMap.get(position1)[0];
//                pivotY=xyMap.get(position1)[1];
                vp_text.setText((position1 + 1) + "/" + urls.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delImgDialog = new DelImgDialog
                        (ImgAlbumActivity.this, R.style.MyDialog, new DelImgDialog
                                .LeaveMyDialogListener() {

                            @Override
                            public void onClick(View view) {

                                switch (view.getId()) {
                                    case R.id.button_ok:
                                        delImgDialog.dismiss();
                                        OkGo.<String>post(Urls.Url_DeleteImg).tag(this)
                                                .params("userid", PreferenceManager.getUserId
                                                        (ImgAlbumActivity.this))
                                                .params("nameurl", urls.get(position))
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String>
                                                                                  response) {
                                                        Gson gson = new Gson();
                                                        LoginBean loginBean = gson.fromJson
                                                                (response.body(), LoginBean
                                                                        .class);
                                                        if (loginBean.getStatus().equals
                                                                ("success")) {
                                                            urls.remove(position);

                                                            Log.i("TAG", "urls.size: " + urls
                                                                    .size());
                                                            Log.i("TAG", "urls.size: " + urls
                                                                    .toString());
                                                            if (position != 0) {
                                                                position = position - 1;
                                                            }

                                                            imageViews.clear();
                                                            refresh();
                                                        }


                                                    }
                                                });
                                        break;
                                    case R.id.button_cancel:
                                        delImgDialog.dismiss();
                                        break;
                                }


                            }
                        });
                delImgDialog.setCancelable(true);

                //修改弹窗位置
//                changeDialogLocation(addFriendDialog);

                delImgDialog.show();


            }
        });

    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        vp_ll = (LinearLayout) findViewById(R.id.vp_ll);
        vp_text = (TextView) findViewById(R.id.vp_text);
    }

    private void initData() {

        urls = getIntent().getStringArrayListExtra("urls");
        Log.i("TAG", "urls.size: " + urls.size());

        refresh();

    }

    private void refresh() {
        for (int i = 0; i < urls.size(); i++)//获取图片，设置PhotoView，加到ViewPager当中
        {
            PhotoView photoView = new PhotoView(mContext);
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {//单击图片退出大图
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    ImgAlbumActivity.this.finish();
                }
            });
            Glide.with(this).load(urls.get(i)).into(photoView);
            imageViews.add(photoView);
        }
        vp.setAdapter(adapter);
        vp.setCurrentItem(position, true);
        vp_text.setText((position + 1) + "/" + urls.size());

    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = imageViews.get(position);
            container.addView(view);
            return view;
        }

    }

    /**
     * 重写finish方法
     */
    @Override
    public void finish() {
        //大图页面是全屏，小图页面非全屏，从全屏退到非全屏页面会产生抖动现象，因此退出前设置成非全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, pivotX, pivotY);//动画从正常大小缩小至0
        scaleAnimation.setDuration(350);
        scaleAnimation.setFillAfter(true);
        vp_ll.startAnimation(scaleAnimation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                ImgAlbumActivity.super.finish();//动画结束时调用Activity的finish方法
                overridePendingTransition(0, 0);//紧跟着禁用Activity默认动画
            }
        });


    }

}
