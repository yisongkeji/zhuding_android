package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.cy.translucentparent.StatusNavUtils;
import com.foreseers.chat.bean.FriendTimeBean;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.fragment.ChatFragment;
import com.foreseers.chat.fragment.FriendFragment;
import com.foreseers.chat.fragment.MatchFragment;
import com.foreseers.chat.fragment.MyFragment;
import com.foreseers.chat.fragment.ShopFragment;
import com.foreseers.chat.service.MediaService;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
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

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;
    @BindView(R.id.buttom_friend)
    BottomBarItem buttomFriend;
    @BindView(R.id.img_main)
    ImageView imgMain;

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
    private MessageListener messageListener;
    private ContactListener contactListener;
    private int messNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusNavUtils.setStatusBarColor(this, 0x00000000);
        initView();
        initData();
        initListener();
        userid = GetLoginTokenUtil.getUserId(this);
        OkGo.<String>post(Urls.Url_Countage).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        friendTimeBean = gson.fromJson(response.body(),
                                FriendTimeBean.class);
                        dataBean = friendTimeBean.getData();
                        for (int i = 0; i < dataBean.size(); i++) {


                            Log.d("TAG@@@", i + "网络请求成功：" + "   hour：" + dataBean.get(i).getHour());
                            if (dataBean.get(i).getHour() < 8 * 3600000) {//成为好友小于8小时，不可查看清晰头像
                                Log.d("TAG@@@", "网络请求成功：" + "   hour888888888888888");
                                Intent intent = new Intent(MainActivity.this, MediaService.class);
                                intent.putExtra("type", 0);
                                intent.putExtra("hour", dataBean.get(i).getHour());
                                intent.putExtra("friendid", dataBean.get(i).getFriend());
                                intent.putExtra("userid", dataBean.get(i).getUserid());
                                startService(intent);
                            } else if (dataBean.get(i).getHour() < 24 * 3600000)
                            {//成为好友小于24小时，大于8小时，可查看清晰头像，聊天不可发送图片
                                Log.d("TAG@@@", "网络请求成功：" + "   hour4444444444444444");
                                Intent intent = new Intent(MainActivity.this, MediaService.class);
                                intent.putExtra("type", 1);
                                intent.putExtra("hour", dataBean.get(i).getHour());
                                intent.putExtra("friendid", dataBean.get(i).getFriend());
                                intent.putExtra("userid", dataBean.get(i).getUserid());
                                startService(intent);
                            } else if (dataBean.get(i).getHour() < 72 * 3600000)
                            {//成为好友小于72小时，大于24小时，可查看清晰头像，聊天可发送图片，不可查看相册
                                Log.d("TAG@@@", "网络请求成功：" + "   hour777777777777777777");
                                Intent intent = new Intent(MainActivity.this, MediaService.class);
                                intent.putExtra("type", 2);
                                intent.putExtra("hour", dataBean.get(i).getHour());
                                intent.putExtra("friendid", dataBean.get(i).getFriend());
                                intent.putExtra("userid", dataBean.get(i).getUserid());
                                startService(intent);
                            } else {
                                OkGo.<String>post(Urls.Url_FriendTime).tag(this)
                                        .params("userid", userid)
                                        .params("friendid", dataBean.get(i).getFriend())
                                        .params("lookhead", "1")
                                        .params("sendpix", "1")
                                        .params("lookimages", "1")
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {
//                                                Log.d("TAG@@@", "OkGo");
//                                                Gson gson = new Gson();
//                                                LoginBean bean = gson.fromJson(response.body(),
// LoginBean.class);
//
                                            }
                                        });
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

//        聊天
        chatFragment = new ChatFragment();
        mFragmentList.add(chatFragment);

//        朋友
        friendFragment = new FriendFragment();
        mFragmentList.add(friendFragment);


//        匹配
        matchFragment = new MatchFragment();
        mFragmentList.add(matchFragment);


//        商店
        shopFragment = new ShopFragment();
        mFragmentList.add(shopFragment);


//        個人
        myFragment = new MyFragment();
        mFragmentList.add(myFragment);


        changeFragment(2);
        mBottomBarLayout.setCurrentItem(2);
    }


    private void initData() {


    }

    private void initListener() {

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int
                    currentPosition) {
                changeFragment(currentPosition);
                switch (currentPosition) {
                    case 0:
//                        mBottomBarLayout.hideNotify(0);
                        break;
                    case 1:
                        mBottomBarLayout.hideNotify(1);
                        break;
                }


            }
        });

        messageListener = new MessageListener();
        contactListener = new ContactListener();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        EMClient.getInstance().contactManager().setContactListener(contactListener);

    }

    private void changeFragment(int currentPosition) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
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
                    break;

                default:
                    break;
            }
        }


    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EMClient.getInstance().contactManager().removeContactListener(contactListener);

    }

    @OnClick(R.id.img_main)
    public void onViewClicked() {
        mBottomBarLayout.setCurrentItem(2);
    }


    public class MessageListener implements EMMessageListener {
        @Override
        public void onMessageReceived(List<EMMessage> list) {//收到消息
            for (EMMessage message : list) {
                mHandler.obtainMessage(GETmessNum).sendToTarget();
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

    }

    public class ContactListener implements EMContactListener {

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
            mHandler.obtainMessage(DATASUCCESS).sendToTarget();

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

}