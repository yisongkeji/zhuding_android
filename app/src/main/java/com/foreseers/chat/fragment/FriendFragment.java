package com.foreseers.chat.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.foreseers.chat.activity.ChatActivity;
import com.foreseers.chat.activity.UserAnalyzeLifeBookActivity;
import com.foreseers.chat.activity.UserDetailsActivity;
import com.foreseers.chat.bean.FriendBean;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.foreseers.chat.foreseers.R;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.exception.OkGoException;
import com.lzy.okgo.model.Response;

/**
 * 联系人
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends EaseBaseFragment {
    private static final String TAG = "EaseContactListFragment";
    protected List<EaseUser> contactList;
    protected ListView listView;
    protected boolean hidden;
    protected ImageButton clearSearch;
    protected EditText query;
    protected Handler handler = new Handler();
    protected EaseUser toBeProcessUser;
    protected String toBeProcessUsername;
    protected EaseContactList contactListLayout;
    protected boolean isConflict;
    protected FrameLayout contentContainer;

    private Map<String, EaseUser> contactsMap;
    private LinearLayout addFriend;
    private List<FriendBean.DataBean> dataBeans = new ArrayList<>();
    private Map<String, EaseUser> map = new HashMap<>();
    private EaseUser easeUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //to avoid crash when open app after long time stay in background after user logged into
        // another device
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_chat, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();

        addFriend = headerView.findViewById(R.id.item_layout_friend);
        addFriend.setOnClickListener(clickListener);
        contentContainer = (FrameLayout) getView().findViewById(R.id
                .content_container);

        contactListLayout = (EaseContactList) getView().findViewById(R.id
                .contact_list);
        listView = contactListLayout.getListView();
        listView.addHeaderView(headerView);
        //search
        query = (EditText) getView().findViewById(com.hyphenate.easeui.R.id.query);
        clearSearch = (ImageButton) getView().findViewById(com.hyphenate.easeui.R.id.search_clear);


    }

    @Override
    protected void setUpView() {


        OkGo.<String>post(Urls.Url_GetFriend).tag(this)
                .params("userid", GetLoginTokenUtil.getUserId(getActivity()))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        FriendBean friendBean = gson.fromJson(response.body(), FriendBean.class);
                        if (friendBean.getStatus().equals("success")) {

                            dataBeans = friendBean.getData();
                            for (int i = 0; i < dataBeans.size(); i++) {
                                easeUser = new EaseUser(dataBeans.get(i).getUsername());
                                map.put(i + "", easeUser);
                            }
                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();

                        }

                    }
                });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    String username = user.getUsername();
                    // 进入用户详情页

                    Intent intent = new Intent(getActivity(), UserDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid", username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


        EMClient.getInstance().addConnectionListener(connectionListener);

        contactList = new ArrayList<EaseUser>();
        getContactList();
        //init list
        contactListLayout.init(contactList);

        if (listItemClickListener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                    listItemClickListener.onListItemClicked(user);
                }
            });
        }

        query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactListLayout.filter(s);
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

        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
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


    /**
     * move user to blacklist
     */
    protected void moveToBlacklist(final String username) {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st1 = getResources().getString(com.hyphenate.easeui.R.string
                .Is_moved_into_blacklist);
        final String st2 = getResources().getString(com.hyphenate.easeui.R.string
                .Move_into_blacklist_success);
        final String st3 = getResources().getString(com.hyphenate.easeui.R.string
                .Move_into_blacklist_failure);
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    //move to blacklist
                    EMClient.getInstance().contactManager().addUserToBlackList(username, false);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st2, Toast.LENGTH_SHORT).show();
                            refresh();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st3, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();

    }

    // refresh ui
    public void refresh() {
        getContactList();
        contactListLayout.refresh();
    }


    @Override
    public void onDestroy() {

        EMClient.getInstance().removeConnectionListener(connectionListener);

        super.onDestroy();
    }


    /**
     * get contact list and sort, will filter out users in blacklist
     */
    protected void getContactList() {
        contactList.clear();
        if (contactsMap == null) {
            return;
        }
        synchronized (this.contactsMap) {
            Iterator<Map.Entry<String, EaseUser>> iterator = contactsMap.entrySet().iterator();
            List<String> blackList = EMClient.getInstance().contactManager()
                    .getBlackListUsernames();
            while (iterator.hasNext()) {
                Map.Entry<String, EaseUser> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check
                // if this is new app
                if (!entry.getKey().equals("item_new_friends")
                        && !entry.getKey().equals("item_groups")
                        && !entry.getKey().equals("item_chatroom")
                        && !entry.getKey().equals("item_robots")) {
                    if (!blackList.contains(entry.getKey())) {
                        //filter out users in blacklist
                        EaseUser user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
                        contactList.add(user);
                    }
                }
            }
        }

        // sorting
        Collections.sort(contactList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNickname().compareTo(rhs.getNickname());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });

    }


    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE ||
                    error == EMError.SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        onConnectionDisconnected();
                    }

                });
            }
        }

        @Override
        public void onConnected() {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    onConnectionConnected();
                }

            });
        }
    };
    private EaseContactListFragment.EaseContactListItemClickListener listItemClickListener;


    protected void onConnectionDisconnected() {

    }

    protected void onConnectionConnected() {

    }

    /**
     * set contacts map, key is the hyphenate id
     *
     * @param contactsMap
     */
    public void setContactsMap(Map<String, EaseUser> contactsMap) {
        this.contactsMap = contactsMap;
    }

    public interface EaseContactListItemClickListener {
        /**
         * on click event for item in contact list
         *
         * @param user --the user of item
         */
        void onListItemClicked(EaseUser user);
    }

    /**
     * set contact list item click listener
     *
     * @param listItemClickListener
     */
    public void setContactListItemClickListener(EaseContactListFragment
                                                        .EaseContactListItemClickListener
                                                        listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    protected class HeaderItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_layout_friend:
                    // 进入申请与通知页面
//                    startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));


                    break;

                default:
                    break;
            }
        }

    }

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    setContactsMap(map);
                    break;
                case DATAFELLED:

                    break;
            }
        }
    };

}
