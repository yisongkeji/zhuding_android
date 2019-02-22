package com.foreseers.chat.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foreseers.chat.bean.AnalyzeLifeBookBean;
import com.foreseers.chat.bean.InquireFriendBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.dialog.AddFriendDialog;
import com.foreseers.chat.dialog.AddFriendErrorDialog;
import com.foreseers.chat.dialog.NoFriendNumberDialog;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 好友详情
 */
public class UserDetailsActivity extends BaseActivity {


    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;

    @BindView(R.id.text_user_details_name)
    TextView textUserDetailsName;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.progress_text)
    TextView progressText;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_age)
    TextView textAge;
    @BindView(R.id.text_location)
    TextView textLocation;
    @BindView(R.id.chat_user_details)
    ImageView chatUserDetails;
    @BindView(R.id.img_add_friend)
    ImageView imgAddFriend;

    @BindView(R.id.layout_analyze_life_book)
    LinearLayout layoutAnalyzeLifeBook;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.layout_wipe)
    LinearLayout layoutWipe;
    private NoFriendNumberDialog noFriendNumberDialog;
    private AddFriendDialog addFriendDialog;
    private Intent intent;
    private String userid;
    private Bundle bundle;
    private String username;
    private String sex;
    private int age;
    private int num;
    private int distance;
    private int userscore;
    private AddFriendErrorDialog addFriendErrorDialog;
    private AnalyzeLifeBookBean analyzeLifeBookBean;
    private AnalyzeLifeBookBean.DataBean dataBean;
    private String avatar;
    private List<AnalyzeLifeBookBean.DataBean.ImagesBean> imagesBeans = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        initView();
        initData();


    }

    private void initView() {
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    layoutWipe.setVisibility(View.VISIBLE);
                } else {
                    layoutWipe.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initData() {
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        userid = bundle.getString("userid");

        refresh();

    }

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:

                    //设置图片集合
                    banner.update(imgList);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();


                    myTitlebar.setTitle(username);
                    textUserDetailsName.setText(username);
                    switch (sex) {
                        case "F":

                            textSex.setText(R.string.woman);
                            break;
                        case "M":

                            textSex.setText(R.string.man);
                            break;

                        default:
                            break;
                    }
                    textAge.setText(age + "");
                    textNum.setText(num + "");

                    textLocation.setText(distance + "km");

                    progressText.setText(userscore + "");
                    break;
                case DATAFELLED:
                    Toast.makeText(UserDetailsActivity.this, "网络连接失败", Toast
                            .LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @OnClick({R.id.img_add_friend, R.id.layout_analyze_life_book, R.id.chat_user_details, R.id
            .layout_wipe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add_friend://添加好友

                OkGo.<String>post(Urls.Url_UserFriend).tag(this)
                        .params("facebookid", GetLoginTokenUtil.getFaceBookId(this))
                        .params("friendid", userid)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();

                                InquireFriendBean inquireFriendBean = gson.fromJson(response.body(),
                                        InquireFriendBean.class);
                                if (inquireFriendBean.getStatus().equals("success")) {
                                    switch (inquireFriendBean.getData().getStatus()) {
                                        case 0://可以添加好友
                                            addFriendDialog = new AddFriendDialog
                                                    (UserDetailsActivity.this, R.style.MyDialog,
                                                            username, userid + "",
                                                            inquireFriendBean
                                                                    .getData().getUserint()
                                                            , new
                                                            AddFriendDialog.LeaveMyDialogListener
                                                                    () {

                                                                @Override
                                                                public void onClick(View view) {
                                                                    addFriendDialog.dismiss();
                                                                }
                                                            });
                                            addFriendDialog.setCancelable(true);

                                            //修改弹窗位置
                                            changeDialogLocation(addFriendDialog);

                                            addFriendDialog.show();

                                            break;
                                        case 1://自己好友位已满
                                            noFriendNumberDialog = new NoFriendNumberDialog
                                                    (UserDetailsActivity.this,
                                                            R.style.MyDialog, new
                                                            NoFriendNumberDialog
                                                                    .LeaveMyDialogListener() {

                                                                @Override
                                                                public void onClick(View view) {
                                                                    noFriendNumberDialog.dismiss();
                                                                }
                                                            });

                                            noFriendNumberDialog.setCancelable(true);

                                            //修改弹窗位置
                                            changeDialogLocation(noFriendNumberDialog);

                                            noFriendNumberDialog.show();
                                            break;

                                        case 2://目标好友位已满

                                            addFriendErrorDialog = new AddFriendErrorDialog
                                                    (UserDetailsActivity.this,
                                                            R.style
                                                                    .MyDialog, new
                                                            AddFriendErrorDialog
                                                                    .LeaveMyDialogListener
                                                                    () {
                                                                @Override
                                                                public void onClick(View view) {
                                                                    addFriendErrorDialog.dismiss();
                                                                }
                                                            });
                                            //修改弹窗位置
                                            changeDialogLocation(addFriendErrorDialog);
                                            addFriendErrorDialog.show();

                                            break;
                                    }
                                }
                            }
                        });


                break;


//            case R.id.layout_remark:
//                intent = new Intent(this, RemarkActivity.class);
//                startActivity(intent);
//                break;

            case R.id.layout_analyze_life_book://我與TA的詳細分析
                intent = new Intent(this, UserAnalyzeLifeBookActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                break;

            case R.id.chat_user_details://发消息
                intent = new Intent(this, ChatActivity.class);
                bundle = new Bundle();
                bundle.putString(EaseConstant.EXTRA_USER_ID, userid + "");
                bundle.putString("username", username);
                bundle.putString(EaseConstant.EXTRA_USER_AVATAR, avatar);
                intent.putExtras(bundle);

                startActivity(intent);
                break;

            case R.id.layout_wipe://擦照片
                OkGo.<String>post(Urls.Url_Wipe).tag(this)
                        .params("userid", GetLoginTokenUtil.getUserId(this))
                        .params("caid", userid)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                imgList.clear();
                                refresh();
                            }
                        });
                break;
            default:
                break;
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).error(R.mipmap.default_image).into(imageView);
        }

    }

    private void changeDialogLocation(Dialog dialog) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.x = 0; //x小于0左移，大于0右移
        p.y = -300; //y小于0上移，大于0下移
        dialogWindow.setAttributes(p);
    }

    public void refresh() {
        OkGo.<String>post(Urls.Url_AnalyzeLifeBook).tag(this)
                .params("uid", GetLoginTokenUtil.getUserId(this))
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            analyzeLifeBookBean = gson.fromJson(response.body(),
                                    AnalyzeLifeBookBean.class);

                            dataBean = analyzeLifeBookBean.getData();
                            username = dataBean.getName();
                            sex = dataBean.getSex();
                            age = dataBean.getAge();
                            num = dataBean.getNum();
                            distance = dataBean.getDistance();
                            userscore = dataBean.getUserscore();
                            avatar = dataBean.getHead();

                            imgList.add(avatar);
                            imagesBeans = dataBean.getImages();
                            switch (dataBean.getFriend()) {
                                case 0://是好友
                                    switch (dataBean.getLookimages()) {
                                        case 0://模糊图片
                                            for (int i = 0; i < imagesBeans.size(); i++) {
                                                imgList.add(imagesBeans.get(i).getSpare());
                                            }
                                            break;

                                        case 1://清晰图片
                                            for (int i = 0; i < imagesBeans.size(); i++) {
                                                imgList.add(imagesBeans.get(i).getImage());
                                            }
                                            break;

                                    }


                                    break;
                                case 1://不是好友
                                    //只能模糊图片
                                    for (int i = 0; i < imagesBeans.size(); i++) {
                                        imgList.add(imagesBeans.get(i).getSpare());
                                    }
                                    break;
                            }


                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();


                        } else if (loginBean.getStatus().equals("fail")) {
                            mHandler.obtainMessage(DATAFELLED).sendToTarget();

                        }
                    }
                });

    }
}
