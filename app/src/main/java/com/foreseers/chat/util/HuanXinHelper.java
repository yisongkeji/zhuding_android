package com.foreseers.chat.util;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.foreseers.chat.activity.MainActivity;
import com.foreseers.chat.bean.Constant;
import com.foreseers.chat.db.DemoDBManager;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.domain.InviteMessage;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.util.EMLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class HuanXinHelper {
    private static HuanXinHelper instance = null;
    private ExecutorService executor;
    private Context appContext;
    private EaseUI easeUI;
    private SharedPreferences sharedPreferences;
    private InviteMessgeDao inviteMessgeDao;

    private HuanXinHelper() {
        executor = Executors.newCachedThreadPool();
    }

    public synchronized static HuanXinHelper getInstance() {
        if (instance == null) {
            instance = new HuanXinHelper();
        }
        return instance;
    }

    public void init(Context context) {
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(true);


//        EMOptions options = initChatOptions();
//        options.setRestServer("118.193.28.212:31080");
//        options.setIMServer("118.193.28.212");
//        options.setImPort(31097);

        //use default options if options is null
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;

            //debug mode, you'd better set it to false, if you want release your App officially.
            EMClient.getInstance().setDebugMode(true);
            //get easeui instance
            easeUI = EaseUI.getInstance();
            //to set user's profile and avatar
            setEaseUIProviders();
            //initialize preference manager
//            PreferenceManager.init(context);
            //initialize profile manager

            setGlobalListeners();
            initDbDao();
        }
    }

    private void setEaseUIProviders() {
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    private EaseUser getUserInfo(String username) {
        sharedPreferences = appContext.getSharedPreferences("user", MODE_PRIVATE);
        //获取 EaseUser实例, 这里从内存中读取
        //如果你是从服务器中读读取到的，最好在本地进行缓存
        EaseUser user = null;
        //如果用户是本人，就设置自己的头像
        if (username.equals(EMClient.getInstance().getCurrentUser())) {
            user = new EaseUser(username);
            user.setAvatar((String) sharedPreferences.getString("url", ""));
            user.setNickname((String) sharedPreferences.getString("nick", ""));
            return user;
        }
        user = new EaseUser(username);
        String info = sharedPreferences.getString(username, "");
        if (info.split("&").length > 1) {
            user.setAvatar(info.split("&")[1]);
            user.setNickname(info.split("&")[0]);
        }
        Log.i("zcb", "头像：" + user.getAvatar());
        return user;
    }

    private void initDbDao() {
        inviteMessgeDao = new InviteMessgeDao(appContext);
//        userDao = new UserDao(appContext);
    }


    EMMessageListener messageListener;

    protected void setGlobalListeners() {
        registerMessageListener();
        registerContactListener();


    }

    protected void registerContactListener() {
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
    }

    protected void registerMessageListener() {

        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {

                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    //接收并处理扩展消息
                    String userName = message.getStringAttribute(Constant.USER_NAME, "");
                    String userId = message.getStringAttribute(Constant.USER, "");
                    String userPic = message.getStringAttribute(Constant.HEAD_IMAGE_URL, "");
                    String hxIdFrom = message.getFrom();
                    EaseUser easeUser = new EaseUser(hxIdFrom);
                    easeUser.setAvatar(userPic);
                    easeUser.setNickname(userName);
                    sharedPreferences.edit().putString(hxIdFrom, userName + "&" + userPic).commit();

                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);


    }

    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();

            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getGroupId() == null && inviteMessage.getFrom().equals
                        (username)) {
                    inviteMessgeDao.deleteMessage(username);
                }
            }
            // save invitation as message
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            msg.setReason(reason);

            // set invitation status
            msg.setStatus(InviteMessage.InviteMessageStatus.BEINVITEED);
            notifyNewInviteMessage(msg);
        }

        @Override
        public void onFriendRequestAccepted(String username) {
            //好友请求被同意

            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getFrom().equals(username)) {
                    return;
                }
            }
            // save invitation as message
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
//            showToast(username + " accept your to be friend");
            msg.setStatus(InviteMessage.InviteMessageStatus.BEAGREED);
            notifyNewInviteMessage(msg);
//
//            OkGo.<String>post(Urls.Url_ADDFriend).tag(this)
//                    .params("userid",EMClient.getInstance().getCurrentUser())
//                    .params("friendid",username)
//                    .params("reation",0)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//
//                            Log.e("friend", "onSuccess: 添加好友被同意" );
//                        }
//                    });
        }

        @Override
        public void onFriendRequestDeclined(String username) {
            //好友请求被拒绝
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法
//            OkGo.<String>post(Urls.Url_ADDFriend).tag(this)
//                    .params("userid",EMClient.getInstance().getCurrentUser())
//                    .params("friendid",username)
//                    .params("reation",2)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//
//                        }
//                    });


        }


        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法
        }


    }

    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * logout
     *
     * @param unbindDeviceToken
     *            whether you need unbind your device token
     * @param callback
     *            callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * save and notify invitation message
     * @param msg
     */
    private void notifyNewInviteMessage(InviteMessage msg){
        if(inviteMessgeDao == null){
            inviteMessgeDao = new InviteMessgeDao(appContext);
        }
        inviteMessgeDao.saveMessage(msg);
        //increase the unread message count
        inviteMessgeDao.saveUnreadMessageCount(1);
        // notify there is new message
        getNotifier().vibrateAndPlayTone(null);
    }
    /**
     * get instance of EaseNotifier
     * @return
     */
    public EaseNotifier getNotifier(){
        return easeUI.getNotifier();
    }


    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void reset(){

        DemoDBManager.getInstance().closeDB();
    }
}
