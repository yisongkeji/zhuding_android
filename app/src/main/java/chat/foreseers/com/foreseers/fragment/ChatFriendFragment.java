package chat.foreseers.com.foreseers.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chat.foreseers.com.foreseers.R;

/**
 * 好友聊天
 * * A simple {@link Fragment} subclass.
 */
public class ChatFriendFragment extends EaseBaseFragment {

    @BindView(R.id.ease_conversation_list)
    EaseConversationList easeConversationList;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.fl_error_item)
    FrameLayout errorItemContainer;
    private String TAG = "ChatFriendFragment";
    Unbinder unbinder;
    protected ImageButton clearSearch;
    protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
    protected EditText query;
    protected boolean isConflict;
    private EaseConversationListFragment.EaseConversationListItemClickListener
            listItemClickListener;
    private final static int MSG_REFRESH = 2;
    protected boolean hidden;


    public ChatFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_friend, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initView() {

//        FragmentManager childFragmentManager = getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
//
//        if (conversationListFragment == null) {
//            conversationListFragment = new EaseConversationListFragment();
//            fragmentTransaction.add(R.id.layout,
//                    conversationListFragment).commit();
//        } else {
//            fragmentTransaction.show(conversationListFragment).commit();
//        }
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        clearSearch = (ImageButton) getView().findViewById(com.hyphenate.easeui.R.id.search_clear);
        query = (EditText) getView().findViewById(com.hyphenate.easeui.R.id.query);

    }

    @Override
    protected void setUpView() {
        conversationList.addAll(loadConversationList());
        easeConversationList.init(conversationList);

        if (listItemClickListener != null) {
            easeConversationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMConversation conversation = easeConversationList.getItem(position);
                    listItemClickListener.onListItemClicked(conversation);
                }
            });
        }

        EMClient.getInstance().addConnectionListener(connectionListener);

        query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                easeConversationList.filter(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.getText().clear();
                hideSoftKeyboard();
            }
        });
        easeConversationList.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });


    }

    /**
     * load conversation list
     *
     * @return +
     */
    protected List<EMConversation> loadConversationList() {
        // get all conversations
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager()
                .getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        /**
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
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
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long,
                    EMConversation> con2) {

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
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE ||
                    error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError
                    .USER_KICKED_BY_OTHER_DEVICE) {
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

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
                    easeConversationList.refresh();
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * disconnected with server
     */
    protected void onConnectionDisconnected() {
        errorItemContainer.setVisibility(View.VISIBLE);
    }
    /**
     * connected to server
     */
    protected void onConnectionConnected(){
        errorItemContainer.setVisibility(View.GONE);
    }
    /**
     * refresh ui
     */
    public void refresh() {
        if(!handler.hasMessages(MSG_REFRESH)){
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

}
