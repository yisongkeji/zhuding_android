package com.foreseers.chat.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.foreseers.chat.R;
import com.foreseers.chat.activity.ChatActivity;
import com.foreseers.chat.bean.Constant;
import com.foreseers.chat.bean.UserBean;
import com.foreseers.chat.db.DemoDBManager;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.domain.InviteMessage;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
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
        if (EaseUI.getInstance()
                .init(context, options)) {
            appContext = context;

            //debug mode, you'd better set it to false, if you want release your App officially.
            EMClient.getInstance()
                    .setDebugMode(true);
            //get easeui instance
            easeUI = EaseUI.getInstance();
            //to set user's profile and avatar
            setEaseUIProviders();
            //initialize preference manager
            PreferenceManager.init(context);
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
        //set options
        easeUI.setSettingsProvider(new EaseUI.EaseSettingsProvider() {

            @Override
            public boolean isSpeakerOpened() {
                return true;
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                if (message == null) {
                    return true;
                } else {
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // get user or group id which was blocked to show message notifications
                    if (message.getChatType() == EMMessage.ChatType.Chat) {
                        chatUsename = message.getFrom();
                        //                        notNotifyIds = demoModel.getDisabledIds();
                        return true;
                    } else {
                        chatUsename = message.getTo();
                        //                        notNotifyIds = demoModel.getDisabledGroups();
                        return false;
                    }

                    //                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                    //                        return true;
                    //                    } else {
                    //                        return false;
                    //                    }
                }
            }
        });

        //set notification options, will use default if you don't set it
        easeUI.getNotifier()
                .setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

                    @Override
                    public String getTitle(EMMessage message) {
                        //you can update title here
                        return null;
                    }

                    @Override
                    public int getSmallIcon(EMMessage message) {
                        //you can update icon here
                        return 0;
                    }

                    @Override
                    public String getDisplayedText(EMMessage message) {
                        // be used on notification bar, different text according the message type.
                        String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                        if (message.getType() == EMMessage.Type.TXT) {
                            ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                        }
                        EaseUser user = getUserInfo(message.getFrom());
                        if (user != null) {
                            if (EaseAtMessageHelper.get()
                                    .isAtMeMsg(message)) {
                                return String.format(appContext.getString(R.string.at_your_in_group), user.getNickname());
                            }
                            return user.getNickname() + ": " + ticker;
                        } else {
                            if (EaseAtMessageHelper.get()
                                    .isAtMeMsg(message)) {
                                return String.format(appContext.getString(R.string.at_your_in_group), message.getFrom());
                            }
                            return message.getFrom() + ": " + ticker;
                        }
                    }

                    @Override
                    public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                        // here you can customize the text.
                        // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                        return null;
                    }

                    @Override
                    public Intent getLaunchIntent(EMMessage message) {
                        // you can set what activity you want display when user click the notification
                        //设置点击通知栏跳转事件
                        Intent intent = new Intent(appContext, ChatActivity.class);
                        // open calling activity if there is call

                        EMMessage.ChatType chatType = message.getChatType();
                        if (chatType == EMMessage.ChatType.Chat) { // single chat message// 单聊信息
                            intent.putExtra("userId", message.getFrom());
                            intent.putExtra("nickname", message.getStringAttribute(Constant.USER_NAME, ""));
                            intent.putExtra("avatar", message.getStringAttribute(Constant.HEAD_IMAGE_URL, ""));
                            intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
                        } else { // group chat message
                            // message.getTo() is the group id
                            intent.putExtra("userId", message.getTo());
                            if (chatType == EMMessage.ChatType.GroupChat) {
                                intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                            } else {
                                intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
                            }
                        }
                        return intent;
                    }
                });
    }

    public EaseUser getUserInfo(String username) {
        sharedPreferences = appContext.getSharedPreferences("user", MODE_PRIVATE);
        //获取 EaseUser实例, 这里从内存中读取
        //如果你是从服务器中读读取到的，最好在本地进行缓存
        EaseUser user = null;
        //如果用户是本人，就设置自己的头像
        if (username.equals(EMClient.getInstance()
                                    .getCurrentUser())) {
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
        EMClient.getInstance()
                .contactManager()
                .setContactListener(new MyContactListener());
    }

    protected void registerMessageListener() {

        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {

                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().notify(message);
                    } else {
                        getNotifier().vibrateAndPlayTone(message);
                    }

                    OkGo.<String>post(Urls.Url_Userquery).tag(this)
                            .params("uid", PreferenceManager.getUserId(appContext))
                            .params("userid", message.getFrom())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Gson gson = new Gson();
                                    UserBean userBean = gson.fromJson(response.body(), UserBean.class);
                                    int userid = userBean.getData()
                                            .getUserid();
                                    int vip = userBean.getData()
                                            .getVip();
                                    String head = userBean.getData()
                                            .getHead();
                                    String name = userBean.getData()
                                            .getUsername();

                                    EaseUser easeUser = new EaseUser(userid + "");
                                    easeUser.setAvatar(head);
                                    easeUser.setNickname(name);

                                    if (sharedPreferences!=null) {
                                        sharedPreferences.edit()
                                                .putString(userid + "", name + "&" + head + "&" + vip)
                                                .commit();
                                    }
                                }
                            });
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
        EMClient.getInstance()
                .chatManager()
                .addMessageListener(messageListener);
    }

    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();

            for (InviteMessage inviteMessage : msgs) {
                if (inviteMessage.getGroupId() == null && inviteMessage.getFrom()
                        .equals(username)) {
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
                if (inviteMessage.getFrom()
                        .equals(username)) {
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
        return EMClient.getInstance()
                .isLoggedInBefore();
    }

    /**
     * logout
     *
     * @param unbindDeviceToken whether you need unbind your device token
     * @param callback          callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance()
                .logout(unbindDeviceToken, new EMCallBack() {

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
     *
     * @param msg
     */
    private void notifyNewInviteMessage(InviteMessage msg) {
        if (inviteMessgeDao == null) {
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
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    void endCall() {
        try {
            EMClient.getInstance()
                    .callManager()
                    .endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized void reset() {

        DemoDBManager.getInstance()
                .closeDB();
    }

    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }
}
