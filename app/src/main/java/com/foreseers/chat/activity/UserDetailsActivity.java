package com.foreseers.chat.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foreseers.chat.bean.AnalyzeLifeBookBean;
import com.foreseers.chat.bean.ErrBean;
import com.foreseers.chat.bean.InquireFriendBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.dialog.AddFriendDialog;
import com.foreseers.chat.dialog.AddFriendErrorDialog;
import com.foreseers.chat.dialog.BlackDialog;
import com.foreseers.chat.dialog.DelFriendDialog;
import com.foreseers.chat.dialog.NoFriendNumberDialog;
import com.foreseers.chat.R;
import com.foreseers.chat.dialog.WipeDialog;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
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

    @BindView(R.id.text_ziwei)
    TextView textZiwei;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_sign)
    TextView textSign;
    @BindView(R.id.text_desc)
    TextView textDesc;
    @BindView(R.id.img_ani)
    ImageView imgAni;


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
    private int friend;
    private DelFriendDialog delFriendDialog;
    private String ziwei;
    private int thirthday;
    private int sevenday;
    private int lookhead;
    private String sign;
    private String desc;
    private AnimationDrawable animationDrawable;
    private WipeDialog wiped;
    private BlackDialog blackDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initView() {

        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopupWind();
            }
        });

        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置图片加载器
        banner.setImageLoader(new GlideUtil.GlideImageLoader());
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    if (lookhead == 1) {
                        layoutWipe.setVisibility(View.GONE);
                    } else if (lookhead == 0) {
                        layoutWipe.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (lookhead == 1) {
                        layoutWipe.setVisibility(View.GONE);
                    } else if (lookhead == 0) {
                        layoutWipe.setVisibility(View.VISIBLE);
                    }
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

        imgAni.setBackgroundResource(R.drawable.start_show);
        animationDrawable = (AnimationDrawable) imgAni.getBackground();


    }

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private final int ANIMATION = 3;


    @OnClick({R.id.img_add_friend, R.id.layout_analyze_life_book, R.id.chat_user_details, R.id
            .layout_wipe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add_friend://添加好友

                if (friend == 1) {
                    OkGo.<String>post(Urls.Url_UserFriend).tag(this)
                            .params("facebookid", GetLoginTokenUtil.getFaceBookId(this))
                            .params("friendid", userid)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Gson gson = new Gson();

                                    InquireFriendBean inquireFriendBean = gson.fromJson(response
                                            .body(), InquireFriendBean.class);
                                    if (inquireFriendBean.getStatus().equals("success")) {
                                        switch (inquireFriendBean.getData().getStatus()) {
                                            case 0://可以添加好友
                                                addFriendDialog = new AddFriendDialog
                                                        (UserDetailsActivity.this, R.style
                                                                .MyDialog, inquireFriendBean
                                                                .getData().getName(), userid,
                                                                inquireFriendBean.getData()
                                                                        .getHead(),
                                                                inquireFriendBean.getData()
                                                                        .getUserint(),
                                                                new AddFriendDialog
                                                                        .LeaveMyDialogListener() {

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
                                                        (UserDetailsActivity.this, R.style
                                                                .MyDialog, new
                                                                NoFriendNumberDialog
                                                                        .LeaveMyDialogListener
                                                                        () {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        noFriendNumberDialog
                                                                                .dismiss();
                                                                    }
                                                                });

                                                noFriendNumberDialog.setCancelable(true);

                                                //修改弹窗位置
                                                changeDialogLocation(noFriendNumberDialog);

                                                noFriendNumberDialog.show();
                                                break;

                                            case 2://目标好友位已满

                                                addFriendErrorDialog = new AddFriendErrorDialog
                                                        (UserDetailsActivity.this, R.style
                                                                .MyDialog, new
                                                                AddFriendErrorDialog
                                                                        .LeaveMyDialogListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        addFriendErrorDialog
                                                                                .dismiss();
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
                } else if (friend == 0) {

                    delFriendDialog = new DelFriendDialog
                            (UserDetailsActivity.this, R.style.MyDialog, new DelFriendDialog
                                    .LeaveMyDialogListener() {

                                @Override
                                public void onClick(View view) {

                                    switch (view.getId()) {
                                        case R.id.button_ok:
                                            delFriendDialog.dismiss();
                                            OkGo.<String>post(Urls.Url_DelFriend).tag(this)
                                                    .params("userid", GetLoginTokenUtil.getUserId
                                                            (UserDetailsActivity.this))
                                                    .params("friendid", userid)
                                                    .params("reation", 2)
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(Response<String>
                                                                                      response) {
                                                            refresh();
                                                        }
                                                    });
                                            break;
                                        case R.id.button_cancel:
                                            delFriendDialog.dismiss();
                                            break;
                                    }

//                                     delFriendDialog.dismiss();
                                }
                            });
                    delFriendDialog.setCancelable(true);

                    //修改弹窗位置
//                changeDialogLocation(addFriendDialog);

                    delFriendDialog.show();


                }

                break;

            case R.id.layout_analyze_life_book://我與TA的詳細分析
                if (sevenday == 1) {
                    intent = new Intent(this, UserAnalyzeLifeBookActivity.class);
                    intent.putExtra("userid", userid);
                    startActivity(intent);
                }


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
                //                                     delFriendDialog.dismiss();
                wiped = new WipeDialog
                        (UserDetailsActivity.this, R.style.MyDialog, new WipeDialog
                                .LeaveMyDialogListener() {

                            @Override
                            public void onClick(View view) {

                                switch (view.getId()) {
                                    case R.id.button_ok:
                                        wiped.dismiss();
                                        OkGo.<String>post(Urls.Url_Wipe).tag(this)
                                                .params("userid", GetLoginTokenUtil.getUserId
                                                        (UserDetailsActivity.this))
                                                .params("caid", userid)
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String>
                                                                                  response) {
                                                        imgAni.setVisibility(View.VISIBLE);
                                                        Gson gson = new Gson();
                                                        LoginBean loginBean = gson.fromJson
                                                                (response.body(), LoginBean.class);
                                                        if (loginBean.getStatus().equals
                                                                ("success")) {
                                                            imgList.clear();
                                                            layoutWipe.setVisibility(View.GONE);
                                                            refresh();
                                                            imgAni.setVisibility(View.VISIBLE);
                                                            animationDrawable.start();
                                                            new Thread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    try {
                                                                        Thread.sleep(12 * 100);
                                                                        getHandler()
                                                                                .obtainMessage
                                                                                        (ANIMATION).sendToTarget();
                                                                    } catch (InterruptedException
                                                                            e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }).start();


                                                        } else if (loginBean.getStatus().equals
                                                                ("fail")) {

                                                            ErrBean errBean = gson.fromJson
                                                                    (response.body(), ErrBean
                                                                            .class);
                                                            if (errBean.getData().getErrCode() ==
                                                                    2000) {
                                                                Toast.makeText
                                                                        (UserDetailsActivity
                                                                                        .this,
                                                                                "擦字数不足",
                                                                                Toast.LENGTH_LONG).show();
                                                            } else {
                                                                Toast.makeText
                                                                        (UserDetailsActivity
                                                                                        .this,
                                                                                "发送失败，请检查网络",
                                                                                Toast.LENGTH_LONG).show();
                                                            }

                                                        }
                                                    }
                                                });
                                        break;
                                    case R.id.button_cancel:
                                        wiped.dismiss();
                                        break;
                                }

                                //                                     delFriendDialog.dismiss();
                            }
                        });
                wiped.setCancelable(true);

                //修改弹窗位置
//                changeDialogLocation(addFriendDialog);

                wiped.show();


                break;
            default:
                break;
        }
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
        switch (msg.what) {
            case DATASUCCESS:
                if (layoutWipe != null) {
                    switch (lookhead) {

                        case 0:
                            layoutWipe.setVisibility(View.VISIBLE);
                            break;

                        case 1:
                            layoutWipe.setVisibility(View.GONE);
                            break;
                    }

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

                    switch (friend) {

                        case 0:
                            imgAddFriend.setBackgroundResource(R.mipmap.icon_data_03);
                            break;

                        case 1:
                            imgAddFriend.setBackgroundResource(R.mipmap.icon_data_01);
                            break;
                    }

                    textAge.setText(age + "");
                    textNum.setText(num + "");
                    textZiwei.setText(ziwei);
                    textLocation.setText(distance + "km +");
                    progressText.setText(userscore + "");
                    textDesc.setText(desc);
                    textSign.setText(sign);

                    if (sevenday == 0) {
                        text1.setText(R.string.life_book);
                        text1.getPaint().setAntiAlias(true);//抗锯齿
                        text1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint
                                .ANTI_ALIAS_FLAG);

                    } else {
                        text1.getPaint().setFlags(0);
                    }
                    if (thirthday == 0) {

                        text2.setText(R.string.ta_life_book);
                        text2.getPaint().setAntiAlias(true);//抗锯齿
                        text2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint
                                .ANTI_ALIAS_FLAG);

                    } else {
                        text2.getPaint().setFlags(0);
                    }

                    OkGo.<String>post(Urls.Url_UserLook).tag(this)
                            .params("userid", GetLoginTokenUtil.getUserId(this))
                            .params("lookid", userid)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                }
                            });
                }

                break;
            case DATAFELLED:
                Toast.makeText(UserDetailsActivity.this, "网络连接失败", Toast
                        .LENGTH_SHORT).show();
                break;
            case ANIMATION:
                imgAni.setVisibility(View.GONE);
                animationDrawable.stop();
                break;
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
                            friend = dataBean.getFriend();
                            ziwei = dataBean.getZiwei();
                            sevenday = dataBean.getSevenday();
                            thirthday = dataBean.getThirthday();
                            desc = dataBean.getUserdesc();
                            sign = dataBean.getObligate();
                            lookhead = dataBean.getLookhead();


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


                            getHandler().obtainMessage(DATASUCCESS).sendToTarget();


                        } else if (loginBean.getStatus().equals("fail")) {
                            getHandler().obtainMessage(DATAFELLED).sendToTarget();

                        }
                    }
                });

    }

    private void initPopupWind() {

        new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .addItemAction(new PopItemAction("举报", PopItemAction.PopItemStyle.Normal, new
                        PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {

                            }
                        }))
                .addItemAction(new PopItemAction("拉黑", PopItemAction.PopItemStyle.Normal, new
                        PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {

                                blackDialog = new BlackDialog(UserDetailsActivity.this, R.style.MyDialog, new BlackDialog.LeaveMyDialogListener() {

                                    @Override
                                    public void onClick(View view) {

                                        switch (view.getId()) {
                                            case R.id.button_ok:
                                                blackDialog.dismiss();
                                                OkGo.<String>post(Urls.Url_AddBlack).tag(this)
                                                        .params("userid", EMClient.getInstance().getCurrentUser())
                                                        .params("blackid", userid)
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(Response<String> response) {

                                                            }
                                                        });

                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            EMClient.getInstance().contactManager().addUserToBlackList(userid, true);
                                                            EMClient.getInstance().contactManager().deleteContact(userid);
                                                        } catch (HyphenateException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).start();
                                                break;
                                            case R.id.button_cancel:
                                                blackDialog.dismiss();
                                                break;
                                        }
                                    }
                                });
                                blackDialog.setCancelable(true);
                                blackDialog.show();


                            }
                        }))
                .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel, new
                        PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {

                            }
                        }))
                .create()
                .show();


    }
}
