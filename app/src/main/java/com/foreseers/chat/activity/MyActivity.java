package com.foreseers.chat.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.MyBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ms.banner.Banner;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;

    @BindView(R.id.img_ani) ImageView imgAni;
    @BindView(R.id.layout_wipe) LinearLayout layoutWipe;
    @BindView(R.id.text_user_details_name) TextView textUserDetailsName;
    @BindView(R.id.img_vip) ImageView imgVip;
    @BindView(R.id.text_num) TextView textNum;
    @BindView(R.id.text_sex) TextView textSex;
    @BindView(R.id.text_age) TextView textAge;
    @BindView(R.id.text_ziwei) TextView textZiwei;
    @BindView(R.id.text_sign) TextView textSign;
    private final int DATASUCCESS = 1;
    private final int DATASUCCESS2 = 2;
    @BindView(R.id.banner) Banner banner;
    private List<String> imgList = new ArrayList<>();
    private MyBean myBean;
    private MyBean.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置自动轮播，默认为true
        banner.setAutoPlay(false);
    }

    @Override
    public void initDatas() {
        getDataFromHttp();
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case DATASUCCESS:
                if (banner != null) {

                    //设置图片加载器
                    banner.setPages(imgList, new HolderCreator<BannerViewHolder>() {
                        @Override
                        public BannerViewHolder createViewHolder() {
                            return new CustomViewHolder();
                        }
                    });
                    banner.start();
                    switch (dataBean.getVip()) {
                        case 0:
                            imgVip.setVisibility(View.GONE);
                            break;
                        case 1:
                            imgVip.setVisibility(View.VISIBLE);
                            break;
                    }

                    textUserDetailsName.setText(dataBean.getName());
                    textNum.setText(dataBean.getNum() + "");
                    textAge.setText(dataBean.getAge() + "");
                    switch (dataBean.getSex()) {
                        case "F":
                            textSex.setText(getActivity().getResources().getString(R.string.woman));
                            break;
                        case "M":
                            textSex.setText(getActivity().getResources().getString(R.string.man));
                            break;
                    }
                    textZiwei.setText(dataBean.getZiwei());
                    textSign.setText(dataBean.getSign());
                }

                break;

            case DATASUCCESS2:

                break;
        }
    }

    private void getDataFromHttp() {
        OkGo.<String>post(Urls.Url_UserShow).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            myBean = gson.fromJson(response.body(), MyBean.class);
                            dataBean = myBean.getData();
                            imgList.add(dataBean.getHead());
                            for (int i = 0; i < dataBean.getImages()
                                    .size(); i++) {
                                imgList.add(dataBean.getImages()
                                                    .get(i));
                            }

                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
                        }
                    }
                });
    }
    class CustomViewHolder implements BannerViewHolder<Object> {

        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回mImageView页面布局
            mImageView = new ImageView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            );
            mImageView.setBackgroundColor(Color.BLACK);
            mImageView.setLayoutParams(params);
            mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return mImageView;
        }

        @Override
        public void onBind(Context context, int position, Object data) {
            // 数据绑定
            Glide.with(context).load(data).into(mImageView);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
