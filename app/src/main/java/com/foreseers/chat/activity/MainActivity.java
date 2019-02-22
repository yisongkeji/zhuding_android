package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.cy.translucentparent.StatusNavUtils;
import com.foreseers.chat.bean.Constant;
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
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.util.EMLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import me.yokeyword.fragmentation.SupportActivity;

import static android.content.ContentValues.TAG;


public class MainActivity extends SupportActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentTransaction transaction;
    private ChatFragment chatFragment;
    private FriendFragment friendFragment;
    private MatchFragment matchFragment;
    private ShopFragment shopFragment;
    private MyFragment myFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusNavUtils.setStatusBarColor(this, 0x00000000);
        initData();
        initListener();
        OkGo.<String>post(Urls.Url_Countage).tag(this)
                .params("userid",GetLoginTokenUtil.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson=new Gson();
                        FriendTimeBean friendTimeBean=gson.fromJson(response.body(),
                                FriendTimeBean.class);
                        List<FriendTimeBean.DataBean> dataBean=friendTimeBean.getData();
                        for (int i = 0; i < dataBean.size(); i++) {


                            Log.d("TAG@@@", i+"网络请求成功：" +"   hour："+dataBean.get(i).getHour());
                            if (dataBean.get(i).getHour()<8){//成为好友小于8小时，不可查看清晰头像
                                Log.d("TAG@@@", "网络请求成功：" +"   hour888888888888888");
                                Intent intent = new Intent(MainActivity.this, MediaService.class);
                                intent.putExtra("type",0);
                                intent.putExtra("hour",8);
                                intent.putExtra("friendid",dataBean.get(i).getFriend());
                                intent.putExtra("userid",dataBean.get(i).getUserid());
                                startService(intent);
                            }else if (dataBean.get(i).getHour() <24)
                            {//成为好友小于24小时，大于8小时，可查看清晰头像，聊天不可发送图片
                                Log.d("TAG@@@", "网络请求成功：" +"   hour4444444444444444");
                                Intent intent = new Intent(MainActivity.this, MediaService.class);
                                intent.putExtra("type",1);
                                intent.putExtra("hour",24);
                                intent.putExtra("friendid",dataBean.get(i).getFriend());
                                intent.putExtra("userid",dataBean.get(i).getUserid());
                                startService(intent);
                            }else if (dataBean.get(i).getHour() <72)
                            {//成为好友小于72小时，大于24小时，可查看清晰头像，聊天可发送图片，不可查看相册
                                Log.d("TAG@@@", "网络请求成功：" +"   hour777777777777777777");
                                Intent intent = new Intent(MainActivity.this, MediaService.class);
                                intent.putExtra("type",2);
                                intent.putExtra("hour",72);
                                intent.putExtra("friendid",dataBean.get(i).getFriend());
                                intent.putExtra("userid",dataBean.get(i).getUserid());
                                startService(intent);
                            }


                        }



                    }
                });
    }


    private void initData() {

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

    private void initListener() {

        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int
                    currentPosition) {
                changeFragment(currentPosition);
            }
        });

//        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
//            @Override
//            public void onMessageReceived(List<EMMessage> list) {
//                for (EMMessage message : list) {
//                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
//                    //接收并处理扩展消息
//                    String userName = message.getStringAttribute(Constant.USER_NAME, "");
//                    String userId = message.getStringAttribute(Constant.USER, "");
//                    String userPic = message.getStringAttribute(Constant.HEAD_IMAGE_URL, "");
//                    String hxIdFrom = message.getFrom();
//                    EaseUser easeUser = new EaseUser(hxIdFrom);
//                    easeUser.setAvatar(userPic);
////                    easeUser.setNick(userName);
//                    getSharedPreferences("user",MODE_PRIVATE).edit().putString(hxIdFrom,userName+"&"+userPic).commit();
//                }
//            }
//
//            @Override
//            public void onCmdMessageReceived(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageRead(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageDelivered(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageRecalled(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageChanged(EMMessage emMessage, Object o) {
//
//            }
//        });



    }

    private void changeFragment(int currentPosition) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
    }


}
