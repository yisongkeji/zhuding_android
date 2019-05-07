package com.foreseers.chat.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.example.com.statusbarutil.StatusBarUtil;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.FriendTimeBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.fragment.ChatFragment;
import com.foreseers.chat.fragment.FriendFragment;
import com.foreseers.chat.fragment.MatchFragment;
import com.foreseers.chat.fragment.MyFragment;
import com.foreseers.chat.fragment.ShopFragment;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.service.MediaService;
import com.foreseers.chat.util.HuanXinHelper;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    private final String TAG = "MainActivity@@@@@";
    @BindView(R.id.fl_content) FrameLayout flContent;
    @BindView(R.id.bbl) BottomBarLayout mBottomBarLayout;
    @BindView(R.id.img_main) ImageView imgMain;
    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_message) TextView textMessage;
    @BindView(R.id.layout_message) LinearLayout layoutMessage;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentTransaction transaction;
    private ChatFragment chatFragment;
    private FriendFragment friendFragment;
    private MatchFragment matchFragment;
    private ShopFragment shopFragment;
    private MyFragment myFragment;
    private FriendTimeBean friendTimeBean;
    private List<FriendTimeBean.DataBean> dataBean = new ArrayList<>();
    private String userid;

    private ContactListener contactListener;
    private int messNum;
    private SharedPreferences sharedPreferences;
    private SoundPool soundPool;
    private int soundID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        if (!HuanXinHelper.getInstance()
                .isLoggedIn()) {
            EMClient.getInstance()
                    .login(PreferenceManager.getUserId(this) + "", "123", new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance()
                                    .groupManager()
                                    .loadAllGroups();
                            EMClient.getInstance()
                                    .chatManager()
                                    .loadAllConversations();
                            Log.d("EMClient", "登录聊天服务器成功！");
                        }

                        @Override
                        public void onProgress(int progress, String status) { }

                        @Override
                        public void onError(int code, String message) {
                            Log.d("EMClient", "登录聊天服务器失败！");
                        }
                    });
        }

        initView();
        initData();
        initListener();
        userid = PreferenceManager.getUserId(this);
        OkGo.<String>post(Urls.Url_Countage).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {

                            friendTimeBean = gson.fromJson(response.body(), FriendTimeBean.class);
                            dataBean = friendTimeBean.getData();
                            for (int i = 0; i < dataBean.size(); i++) {

                                Log.d("TAG@@@", i + "网络请求成功：" + "   hour：" + dataBean.get(i)
                                        .getHour());
                                if (dataBean.get(i)
                                        .getHour() < 8 * 3600000) {//成为好友小于8小时，不可查看清晰头像
                                    Log.d("TAG@@@", "网络请求成功：" + "   hour888888888888888");
                                    Intent intent = new Intent(MainActivity.this, MediaService.class);
                                    intent.putExtra("type", 0);
                                    intent.putExtra("hour", dataBean.get(i)
                                            .getHour());
                                    intent.putExtra("friendid", dataBean.get(i)
                                            .getFriend());
                                    intent.putExtra("userid", dataBean.get(i)
                                            .getUserid());
                                    startService(intent);
                                } else if (dataBean.get(i)
                                        .getHour() < 24 * 3600000) {//成为好友小于24小时，大于8小时，可查看清晰头像，聊天不可发送图片
                                    Log.d("TAG@@@", "网络请求成功：" + "   hour4444444444444444");
                                    Intent intent = new Intent(MainActivity.this, MediaService.class);
                                    intent.putExtra("type", 1);
                                    intent.putExtra("hour", dataBean.get(i)
                                            .getHour());
                                    intent.putExtra("friendid", dataBean.get(i)
                                            .getFriend());
                                    intent.putExtra("userid", dataBean.get(i)
                                            .getUserid());
                                    startService(intent);
                                } else if (dataBean.get(i)
                                        .getHour() < 72 * 3600000) {//成为好友小于72小时，大于24小时，可查看清晰头像，聊天可发送图片，不可查看相册
                                    Log.d("TAG@@@", "网络请求成功：" + "   hour777777777777777777");
                                    Intent intent = new Intent(MainActivity.this, MediaService.class);
                                    intent.putExtra("type", 2);
                                    intent.putExtra("hour", dataBean.get(i)
                                            .getHour());
                                    intent.putExtra("friendid", dataBean.get(i)
                                            .getFriend());
                                    intent.putExtra("userid", dataBean.get(i)
                                            .getUserid());
                                    startService(intent);
                                } else {
                                    OkGo.<String>post(Urls.Url_FriendTime).tag(this)
                                            .params("userid", dataBean.get(i)
                                                    .getUserid())
                                            .params("friendid", dataBean.get(i)
                                                    .getFriend())
                                            .params("lookhead", "1")
                                            .params("sendpix", "1")
                                            .params("lookimages", "1")
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {

                                                }
                                            });
                                }
                            }
                        }
                    }
                });

        OkGo.<String>post(Urls.Url_UserCanums).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });
    }

    private void initView() {
        initSound();
        int position = getIntent().getIntExtra("position", 2);
        //        聊天
        chatFragment = new ChatFragment();
        mFragmentList.add(chatFragment);

        //        朋友
        friendFragment = new FriendFragment();
        mFragmentList.add(friendFragment);

        //        匹配
        matchFragment = new MatchFragment();
        mFragmentList.add(matchFragment);
        Log.d(TAG, "initView: matchFragment ");

        //        商店
        shopFragment = new ShopFragment();
        mFragmentList.add(shopFragment);

        //        個人
        myFragment = new MyFragment();
        mFragmentList.add(myFragment);

        changeFragment(position);
        mBottomBarLayout.setCurrentItem(position);
    }

    private void initData() {

    }

    private void initListener() {

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                changeFragment(currentPosition);
                switch (currentPosition) {
                    case 0:
                        //                        mBottomBarLayout.hideNotify(0);
                        break;
                    case 1:
                        mBottomBarLayout.hideNotify(1);
                        break;
                }
                playSound();
            }
        });

        contactListener = new ContactListener();
    }

    private void changeFragment(int currentPosition) {
        transaction = getSupportFragmentManager().beginTransaction();

        for (int i = 0; i < mFragmentList.size(); i++) {

            if (i == currentPosition) {
                if (!mFragmentList.get(i)
                        .isAdded()) {
                    transaction.add(R.id.fl_content, mFragmentList.get(i));
                }
                transaction.show(mFragmentList.get(i));
            } else {
                if (mFragmentList.get(i)
                        .isAdded()) {
                    transaction.hide(mFragmentList.get(i));
                }
            }
        }

        //        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
    }

    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.bottombar, 1);
    }

    private void playSound() {
        soundPool.play(soundID, 0.1f,      //左耳道音量【0~1】
                       0.5f,      //右耳道音量【0~1】
                       0,         //播放优先级【0表示最低优先级】
                       0,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                       1          //播放速度【1是正常，范围从0~2】
                      );
    }

    private final int DATASUCCESS = 1;
    private final int GETmessNum = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    mBottomBarLayout.showNotify(1);

                    break;

                case GETmessNum:
                    mBottomBarLayout.showNotify(0);
                    if (!chatFragment.isVisible()) {
                        Log.i(TAG, "chatFragment.isVisible(): ");
                        layoutMessage.setVisibility(View.VISIBLE);
                        EMMessage message = (EMMessage) msg.obj;
                        Log.i(TAG, "handleMessage: " + message.getUserName() + "    message.getBody():" + message.getBody());
                        String ticker = EaseCommonUtils.getMessageDigest(message, MyApplication.getContext());
                        if (message.getType() == EMMessage.Type.TXT) {
                            ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                        }
                        EaseUser user = HuanXinHelper.getInstance()
                                .getUserInfo(message.getFrom());
                        textName.setText(user.getNickname());
                        textMessage.setText(ticker);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            layoutMessage.setVisibility(View.GONE);
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        HuanXinHelper huanXinHelper = HuanXinHelper.getInstance();
        huanXinHelper.pushActivity(this);
        EMClient.getInstance()
                .chatManager()
                .addMessageListener(messageListener);
        EMClient.getInstance()
                .contactManager()
                .setContactListener(contactListener);
        EaseUI.getInstance()
                .getNotifier()
                .reset();
    }

    @Override
    protected void onPause() {
        super.onPause();
        HuanXinHelper sdkHelper = HuanXinHelper.getInstance();
        sdkHelper.popActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance()
                .chatManager()
                .removeMessageListener(messageListener);
        EMClient.getInstance()
                .contactManager()
                .removeContactListener(contactListener);
        soundPool.release();


    }

    @OnClick(R.id.img_main)
    public void onViewClicked() {
        mBottomBarLayout.setCurrentItem(2);
    }

    private EMMessageListener messageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {//收到消息
            sharedPreferences = MainActivity.this.getSharedPreferences("user", MODE_PRIVATE);
            for (EMMessage message : messages) {
                //接收并处理扩展消息
                //                String userName = message.getStringAttribute(Constant.USER_NAME, "");
                //                String userId = message.getStringAttribute(Constant.USER, "");
                //                String userPic = message.getStringAttribute(Constant.HEAD_IMAGE_URL, "");
                //                String hxIdFrom = message.getFrom();
                //                EaseUser easeUser = new EaseUser(hxIdFrom);
                //                easeUser.setAvatar(userPic);
                //                easeUser.setNickname(userName);

                //                sharedPreferences.edit().putString(hxIdFrom, userName + "&" + userPic).commit();

                Message message1 = new Message();
                message1.what = GETmessNum;
                message1.obj = message;
                mHandler.sendMessage(message1);
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {  //收到透传消息

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {  //收到已读回执

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) { //收到已送达回执

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {  //消息被撤回

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) { //消息状态变动

        }
    };

    public class ContactListener implements EMContactListener {

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
            mHandler.obtainMessage(DATASUCCESS)
                    .sendToTarget();
        }

        @Override
        public void onFriendRequestAccepted(String s) {
            //好友请求被同意
        }

        @Override
        public void onFriendRequestDeclined(String s) {
            //好友请求被拒绝
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法
        }

        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法
        }
    }

    public void changeBottombar() {
        mBottomBarLayout.hideNotify(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001) {
            shopFragment.afterPurchase(requestCode, resultCode, data);
        }
    }
}