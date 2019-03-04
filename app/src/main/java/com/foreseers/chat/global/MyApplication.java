package com.foreseers.chat.global;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.foreseers.chat.bean.Constant;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.domain.InviteMessage;
import com.foreseers.chat.util.HuanXinHelper;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.util.EMLog;
import com.lzy.okgo.OkGo;

import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by EDZ on 2018/3/27.
 */

public class MyApplication extends Application {

    private InviteMessgeDao inviteMessgeDao;
    private static Context mContext;
    EMConnectionListener connectionListener;
    EMMessageListener messageListener;
    private static MyApplication instance;
    private EaseUI easeUI;
    private SharedPreferences sharedPreferences;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
        initOkGo();
        // 初始化环信SDK
        initEasemob();

    }


    private void initOkGo() {

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        //全局的读取超时时间
//        builder.readTimeout(15000, TimeUnit.MILLISECONDS);
////全局的写入超时时间
//        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
////全局的连接超时时间
//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this);
    }

    private void initEasemob() {


        //init demo helper
        HuanXinHelper.getInstance().init(mContext);

        // 请确保环信SDK相关方法运行在主进程，子进程不会初始化环信SDK（该逻辑在EaseUI.java中）
        if (EaseUI.getInstance().isMainProcess(this)) {
            // 初始化华为 HMS 推送服务, 需要在SDK初始化后执行
//            HMSPushHelper.getInstance().initHMSAgent(instance);
        }




//        inviteMessgeDao = new InviteMessgeDao(mContext);
//        EMOptions options = new EMOptions();
//        options.setAcceptInvitationAlways(false);
//        options.setAutoLogin(true);
//
//        EaseUI.getInstance().init(this, options);
//        easeUI = EaseUI.getInstance();
//        setEaseUIProviders();
//        registerMessageListener();
//        registerFriendListener();

    }

//    private void setEaseUIProviders() {
//        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
//            @Override
//            public EaseUser getUser(String username) {
//                return getUserInfo(username);
//            }
//        });
//    }
//
//    private EaseUser getUserInfo(String username) {
//        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
//        //获取 EaseUser实例, 这里从内存中读取
//        //如果你是从服务器中读读取到的，最好在本地进行缓存
//        EaseUser user = null;
//        //如果用户是本人，就设置自己的头像
//        if (username.equals(EMClient.getInstance().getCurrentUser())) {
//            user = new EaseUser(username);
//            user.setAvatar((String) sharedPreferences.getString("url", ""));
//            user.setNickname((String) sharedPreferences.getString("nick", ""));
//            return user;
//        }
//        user = new EaseUser(username);
//        String info = sharedPreferences.getString(username, "");
//        if (info.split("&").length > 1) {
//            user.setAvatar(info.split("&")[1]);
//            user.setNickname(info.split("&")[0]);
//        }
//        Log.i("zcb", "头像：" + user.getAvatar());
//        return user;
//    }
//
//    protected void registerMessageListener() {
//        messageListener = new EMMessageListener() {
//            private BroadcastReceiver broadCastReceiver = null;
//
//            @Override
//            public void onMessageReceived(List<EMMessage> messages) {
//                for (EMMessage message : messages) {
//                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
//                    //接收并处理扩展消息
//                    String userName = message.getStringAttribute(Constant.USER_NAME, "");
//                    String userId = message.getStringAttribute(Constant.USER, "");
//                    String userPic = message.getStringAttribute(Constant.HEAD_IMAGE_URL, "");
//                    String hxIdFrom = message.getFrom();
//                    EaseUser easeUser = new EaseUser(hxIdFrom);
//                    easeUser.setAvatar(userPic);
//                    easeUser.setNickname(userName);
//                    sharedPreferences.edit().putString(hxIdFrom, userName + "&" + userPic).commit();
//
//                }
//            }
//
//            @Override
//            public void onCmdMessageReceived(List<EMMessage> messages) {
//                for (EMMessage message : messages) {
//                    EMLog.d(TAG, "receive command message");
//                }
//            }
//
//            @Override
//            public void onMessageRead(List<EMMessage> messages) {
//            }
//
//            @Override
//            public void onMessageDelivered(List<EMMessage> message) {
//            }
//
//            @Override
//            public void onMessageRecalled(List<EMMessage> list) {
//
//            }
//
//            @Override
//            public void onMessageChanged(EMMessage message, Object change) {
//            }
//        };
//        EMClient.getInstance().chatManager().addMessageListener(messageListener);
//    }
//
//    private void registerFriendListener() {
//        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
//
//
//            @Override
//            public void onContactInvited(String username, String reason) {
//                //收到好友邀请
//                List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
//
//                for (InviteMessage inviteMessage : msgs) {
//                    if (inviteMessage.getGroupId() == null && inviteMessage.getFrom().equals(username)) {
//                        inviteMessgeDao.deleteMessage(username);
//                    }
//                }
//                // save invitation as message
//                InviteMessage msg = new InviteMessage();
//                msg.setFrom(username);
//                msg.setTime(System.currentTimeMillis());
//                msg.setReason(reason);
//
//                // set invitation status
//                msg.setStatus(InviteMessage.InviteMessageStatus.BEINVITEED);
//            }
//
//            @Override
//            public void onFriendRequestAccepted(String s) {
//                //好友请求被同意
//            }
//
//            @Override
//            public void onFriendRequestDeclined(String s) {
//                //好友请求被拒绝
//            }
//
//            @Override
//            public void onContactDeleted(String username) {
//                //被删除时回调此方法
//            }
//
//
//            @Override
//            public void onContactAdded(String username) {
//                //增加了联系人时回调此方法
//            }
//        });
//    }

    public static MyApplication getInstance() {
        return instance;
    }


}
