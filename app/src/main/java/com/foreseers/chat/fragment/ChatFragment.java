package com.foreseers.chat.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;

import com.foreseers.chat.R;
import com.foreseers.chat.activity.ChatActivity;
import com.foreseers.chat.activity.MainActivity;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMConversationListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * 聊天
 */
public class ChatFragment extends EaseBaseFragment {

    Unbinder unbinder;
    @BindView(R.id.fl_error_item) FrameLayout flErrorItem;
    @BindView(R.id.conversation) EaseConversationList conversation;
    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();

    private ConversationListItemClickListener listItemClickListener;
    protected boolean isConflict;
    protected boolean hidden;
    private final static int MSG_REFRESH = 2;
    private SharedPreferences sharedPreferences;
    private MessageListener messageListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    protected EMConversationListener convListener = new EMConversationListener() {

        @Override
        public void onCoversationUpdate() {
            refresh();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        messageListener = new MessageListener();
        EMClient.getInstance()
                .chatManager()
                .addMessageListener(messageListener);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            return;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void setUpView() {
        conversationList.clear();
        conversationList.addAll(loadConversationList());
        conversation.init(conversationList);

        if (listItemClickListener != null) {
            conversation.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMConversation emconversation = conversation.getItem(position);
                    listItemClickListener.onListItemClicked(emconversation);
                }
            });
        }

        EMClient.getInstance()
                .addConnectionListener(connectionListener);

        conversation.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });

        //跳转聊天页面
        conversation.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                EMConversation emconversation = conversation.getItem(position);
                String username = emconversation.conversationId();
                Log.i("@@@@@", "onItemClick: " + username);
                //                EaseUser user=new EaseUser(username);
                sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
                String info = sharedPreferences.getString(username, "");
                String nickName;
                if (info.split("&").length == 0) {
                    nickName = "";
                } else {
                    nickName = info.split("&")[0];
                }

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(EaseConstant.EXTRA_USER_ID, username);
                bundle.putString("username", nickName);
                intent.putExtras(bundle);
                startActivity(intent);
                ((MainActivity) getActivity()).changeBottombar();
            }
        });
    }

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED || error
                    == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };

    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    onConnectionDisconnected();
                    break;
                case 1:
                    onConnectionConnected();
                    break;

                case MSG_REFRESH: {
                    conversationList.clear();
                    conversationList.addAll(loadConversationList());
                    conversation.refresh();
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * connected to server
     */
    protected void onConnectionConnected() {
        flErrorItem.setVisibility(View.GONE);
    }

    /**
     * disconnected with server
     */
    protected void onConnectionDisconnected() {
        flErrorItem.setVisibility(View.VISIBLE);
    }

    /**
     * refresh ui
     */
    public void refresh() {
        if (!handler.hasMessages(MSG_REFRESH)) {
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    /**
     * load conversation list
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance()
                .chatManager()
                .getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         * *如果排序过程中有新消息，lastMsgTime将会更改
         * *所以使用synchronized来确保最后一条消息的时间戳不会更改。
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages()
                        .size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage()
                                                                        .getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow()
                .getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                                                   .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance()
                .removeConnectionListener(connectionListener);
        EMClient.getInstance()
                .chatManager()
                .removeMessageListener(messageListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (isConflict) {
            outState.putBoolean("isConflict", true);
        }
    }

    public interface ConversationListItemClickListener {
        /**
         * click event for conversation list
         *
         * @param conversation -- clicked item
         */
        void onListItemClicked(EMConversation conversation);
    }

    /**
     * set conversation list item click listener
     *
     * @param listItemClickListener
     */
    public void setConversationListItemClickListener(ConversationListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public class MessageListener implements EMMessageListener {
        @Override
        public void onMessageReceived(List<EMMessage> list) {//收到消息
            mHandler.obtainMessage(DATASUCCESS)
                    .sendToTarget();
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

    private final int DATASUCCESS = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    setUpView();
                    break;

                default:
                    break;
            }
        }
    };
}
