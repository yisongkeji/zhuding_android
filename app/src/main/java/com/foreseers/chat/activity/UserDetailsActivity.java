package com.foreseers.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.foreseers.chat.bean.AnalyzeLifeBookBean;
import com.foreseers.chat.bean.InquireFriendBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.dialog.AddFriendErrorDialog;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.hyphenate.easeui.EaseConstant;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.dialog.AddFriendDialog;
import com.foreseers.chat.dialog.NoFriendNumberDialog;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


/**
 * 好友详情
 */
public class UserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.goback)
    FrameLayout back;
    @BindView(R.id.img_add_friend)
    ImageView imgAddFriend;
    @BindView(R.id.text_user_details_name)
    TextView textUserDetailsName;
    @BindView(R.id.layout_remark)
    LinearLayout layoutRemark;
    @BindView(R.id.layout_analyze_life_book)
    LinearLayout layoutAnalyzeLifeBook;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.chat_user_details)
    LinearLayout chatUserDetails;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.text_location)
    TextView textLocation;
    @BindView(R.id.progress_matching_rate)
    ProgressBar progressMatchingRate;
    @BindView(R.id.progress_text)
    TextView progressText;

    private NoFriendNumberDialog noFriendNumberDialog;
    private AddFriendDialog addFriendDialog;
    private Intent intent;
    private int userid;
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

        initData();


    }

    private void initData() {
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        userid = bundle.getInt("userid");


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
                            num =dataBean.getNum();
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
                    textName.setText(username);
                    textUserDetailsName.setText(username);
                    switch (sex) {
                        case "F":
                            textSex.setBackgroundResource(R.drawable.rounded_layout_pink);
                            textSex.setText("♀" + age);
                            break;
                        case "M":
                            textSex.setBackgroundResource(R.drawable.rounded_layout_blue);
                            textSex.setText("♂" + age);
                            break;

                        default:
                            break;
                    }
                    textNum.setText(num + "");
                    textLocation.setText(R.string.user_location + distance + "");
                    progressMatchingRate.setProgress(userscore);
                    progressText.setText("匹配度" + userscore + "%");
                    break;
                case DATAFELLED:
                    Toast.makeText(UserDetailsActivity.this, "网络连接失败", Toast
                            .LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @OnClick({R.id.goback, R.id.img_add_friend, R.id.layout_remark, R.id
            .layout_analyze_life_book, R.id.chat_user_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goback:
                finish();
                break;
            case R.id.img_add_friend://添加好友

                OkGo.<String>post(Urls.Url_UserFriend).tag(this)
                        .params("facebookid", GetLoginTokenUtil.getFaceBookId(this))
                        .params("friendid", userid)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();

                                InquireFriendBean inquireFriendBean = gson.fromJson(response.body(), InquireFriendBean.class);
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
                bundle.putInt("userid", userid);
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
