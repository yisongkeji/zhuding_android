package com.foreseers.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


/**
 * 好友详情
 */
public class UserDetailsActivity extends BaseActivity {


    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.img_add_friend)
    ImageView imgAddFriend;
    @BindView(R.id.text_user_details_name)
    TextView textUserDetailsName;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_location)
    TextView textLocation;

    @BindView(R.id.progress_text)
    TextView progressText;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.layout_remark)
    LinearLayout layoutRemark;
    @BindView(R.id.layout_analyze_life_book)
    LinearLayout layoutAnalyzeLifeBook;
    @BindView(R.id.chat_user_details)
    LinearLayout chatUserDetails;
    @BindView(R.id.text_age)
    TextView textAge;
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
    }

    private void initData() {
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        userid = bundle.getString("userid");


        OkGo.<String>post(Urls.Url_AnalyzeLifeBook).tag(this)
                .params("uid", GetLoginTokenUtil.getUserId(this))
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            analyzeLifeBookBean = gson.fromJson(response.body(), AnalyzeLifeBookBean.class);

                            dataBean = analyzeLifeBookBean.getData();


                            username = dataBean.getName();
                            sex = dataBean.getSex();
                            age = dataBean.getAge();
                            num = dataBean.getNum();
                            distance = dataBean.getDistance();
                            userscore = dataBean.getUserscore();
                            avatar = dataBean.getHead();


                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();

                        } else if (loginBean.getStatus().equals("fail")) {
                            mHandler.obtainMessage(DATAFELLED).sendToTarget();

                        }
                    }
                });

    }

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    Glide.with(UserDetailsActivity.this).load(dataBean.getHead()).error(R.mipmap.icon_avatar_02).into
                            (imgHead);
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
                    textAge.setText(age+"");
                    textNum.setText(num + "");

                    textLocation.setText( distance + "km");

                    progressText.setText( userscore+"" );
                    break;
                case DATAFELLED:
                    Toast.makeText(UserDetailsActivity.this, "网络连接失败", Toast
                            .LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @OnClick({R.id.img_add_friend, R.id.layout_remark, R.id
            .layout_analyze_life_book, R.id.chat_user_details})
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
                                                            AddFriendDialog.LeaveMyDialogListener() {

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
                                            noFriendNumberDialog = new NoFriendNumberDialog(UserDetailsActivity.this,
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

                                            addFriendErrorDialog = new AddFriendErrorDialog(UserDetailsActivity.this,
                                                    R.style
                                                            .MyDialog, new AddFriendErrorDialog.LeaveMyDialogListener
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


            case R.id.layout_remark:
                intent = new Intent(this, RemarkActivity.class);
                startActivity(intent);
                break;

            case R.id.layout_analyze_life_book://我與TA的詳細分析
                intent = new Intent(this, UserAnalyzeLifeBookActivity.class);
                bundle = new Bundle();
                bundle.putString("userid", userid);
                intent.putExtras(bundle);
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
            default:
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
}
