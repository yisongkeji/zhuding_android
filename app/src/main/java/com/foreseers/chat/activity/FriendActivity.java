package com.foreseers.chat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.bean.FriendBean;
import com.foreseers.chat.bean.FriendNumBean;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseContactList;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File description.
 *
 * @author how
 * @date 2019/7/3
 */
public class FriendActivity extends BaseActivity {

    private static final String TAG = "FriendActivity";
    protected List<EaseUser> contactList = new ArrayList<EaseUser>();
    protected ListView listView;
    protected ImageButton clearSearch;
    protected EditText query;
    protected Handler handler = new Handler();
    protected EaseUser toBeProcessUser;
    protected String toBeProcessUsername;
    protected EaseContactList contactListLayout;
    protected boolean isConflict;
    protected FrameLayout contentContainer;
    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;

    @BindView(R.id.text_num1) TextView textNum1;
    @BindView(R.id.text_num2) TextView textNum2;

    @BindView(R.id.search_clear) ImageButton searchClear;

    @BindView(R.id.layout_FriendFragment) LinearLayout layoutFriendFragment;

    private Map<String, EaseUser> contactsMap;
    private LinearLayout addFriend;
    private List<FriendBean.DataBean> dataBeans = new ArrayList<>();
    private Map<String, EaseUser> map = new HashMap<>();
    private EaseUser easeUser;
    private SharedPreferences sharedPreferences;
    private InviteMessgeDao inviteMessgeDao;
    private String userid;

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;

    //    private EaseContactListFragment.EaseContactListItemClickListener listItemClickListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    if (textNum1 != null) {
                        map.clear();
                        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                        //                            sharedPreferences.edit().clear().commit();

                        for (int i = 0; i < dataBeans.size(); i++) {
                            easeUser = new EaseUser(dataBeans.get(i)
                                                            .getUserid() + "");
                            easeUser.setAvatar(dataBeans.get(i)
                                                       .getUserhead());
                            easeUser.setNickname(dataBeans.get(i)
                                                         .getUsername());

                            sharedPreferences.edit()
                                    .putString(dataBeans.get(i)
                                                       .getUserid() + "", dataBeans.get(i)
                                                       .getUsername() + "&" + dataBeans.get(i)
                                            .getUserhead())
                                    .commit();

                            map.put(dataBeans.get(i)
                                            .getUserid() + "", easeUser);
                            Log.i(TAG, "onSuccessmap: " + map);
                        }

                        setContactsMap(map);
                        refresh();
                    }

                    break;
                case DATAFELLED:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend);
        ButterKnife.bind(this);

        contentContainer = findViewById(R.id.content_container);

        contactListLayout = findViewById(R.id.contact_list);
        listView = contactListLayout.getListView();
        //        listView.addHeaderView(headerView);
        //search
        query = findViewById(com.hyphenate.easeui.R.id.query);
        clearSearch = findViewById(com.hyphenate.easeui.R.id.search_clear);

        myTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入申请与通知页面
                startActivityForResult(new Intent(FriendActivity.this, NewFriendsMsgActivity.class), 0x001);
            }
        });
        userid = PreferenceManager.getUserId(this);

        setUpView();
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    private void setUpView() {
        OkGo.<String>post(Urls.Url_GetFriendNum).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        FriendNumBean friendNumBean = gson.fromJson(response.body(), FriendNumBean.class);
                        if (textNum2 != null) {
                            textNum2.setText(friendNumBean.getData()
                                                     .getFriendNums() + "");
                            textNum1.setText(friendNumBean.getData()
                                                     .getUsenums() + "/");
                        }
                    }
                });

        OkGo.<String>post(Urls.Url_GetFriend).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        FriendBean friendBean = gson.fromJson(response.body(), FriendBean.class);
                        if (friendBean.getStatus()
                                .equals("success")) {
                            dataBeans = friendBean.getData();
                            mHandler.obtainMessage(DATASUCCESS)
                                    .sendToTarget();

                            Log.i(TAG, "onSuccess: " + map);
                        }
                    }
                });

        EMClient.getInstance()
                .addConnectionListener(connectionListener);

        contactList.clear();
        getContactList();

        contactListLayout.init(contactList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {

                    String userid = user.getUsername();
                    String username = user.getNickname();

                    //                    // 进入用户详情页
                    Intent intent = new Intent(FriendActivity.this, UserDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userid", userid);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        //        if (listItemClickListener != null) {
        //            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //
        //                @Override
        //                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //                    EaseUser user = (EaseUser) listView.getItemAtPosition(position);
        //                    listItemClickListener.onListItemClicked(user);
        //                }
        //            });
        //        }

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
                query.getText()
                        .clear();
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

    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                //                getActivity().runOnUiThread(new Runnable() {
                //                    public void run() {
                //                        onConnectionDisconnected();
                //                    }
                //                });
            }
        }

        @Override
        public void onConnected() {
            //            getActivity().runOnUiThread(new Runnable() {
            //                public void run() {
            //                    onConnectionConnected();
            //                }
            //            });
        }
    };

    // refresh ui
    public void refresh() {
        getContactList();
        contactListLayout.refresh();
        if (inviteMessgeDao == null) {
            inviteMessgeDao = new InviteMessgeDao(FriendActivity.this);
        }
        if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
            myTitlebar.setRightImageResource(R.mipmap.icon_footer_profile_06);
        } else {
            myTitlebar.setRightImageResource(R.mipmap.icon_footer_profile_05);
        }
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
            Iterator<Map.Entry<String, EaseUser>> iterator = contactsMap.entrySet()
                    .iterator();
            List<String> blackList = EMClient.getInstance()
                    .contactManager()
                    .getBlackListUsernames();
            while (iterator.hasNext()) {
                Map.Entry<String, EaseUser> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check
                // if this is new app
                if (!entry.getKey()
                        .equals("item_new_friends") && !entry.getKey()
                        .equals("item_groups") && !entry.getKey()
                        .equals("item_chatroom") && !entry.getKey()
                        .equals("item_robots")) {
                    if (!blackList.contains(entry.getKey())) {
                        //filter out users in blacklist
                        EaseUser user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
                        Log.i(TAG, "user: " + user.getAvatar());
                        contactList.add(user);
                    }
                }
            }
        }

        // sorting
        Collections.sort(contactList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter()
                        .equals(rhs.getInitialLetter())) {
                    return lhs.getNickname()
                            .compareTo(rhs.getNickname());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter()
                            .compareTo(rhs.getInitialLetter());
                }
            }
        });
    }

    /**
     * set contacts map, key is the hyphenate id
     *
     * @param contactsMap
     */
    public void setContactsMap(Map<String, EaseUser> contactsMap) {
        this.contactsMap = contactsMap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0x001:
                setUpView();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
        setUpView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance()
                .cancelTag(this);
        EMClient.getInstance()
                .removeConnectionListener(connectionListener);
    }

}
