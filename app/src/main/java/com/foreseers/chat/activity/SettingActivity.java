package com.foreseers.chat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.dialog.InformDialog;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.HuanXinHelper;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.foreseers.chat.util.DataCleanManager.clearAllCache;
import static com.foreseers.chat.util.DataCleanManager.getTotalCacheSize;

public class SettingActivity extends BaseActivity {

    private final String TAG = "SettingActivity@@@@@@";
    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    @BindView(R.id.layout_black)
    LinearLayout layoutBlack;
    @BindView(R.id.layout_about)
    LinearLayout layoutAbout;
    @BindView(R.id.button_out)
    Button buttonOut;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.layout_cache)
    LinearLayout layoutCache;
    @BindView(R.id.layout_temporarychat)
    LinearLayout layoutTemporarychat;
    @BindView(R.id.layout_friendchat)
    LinearLayout layoutFriendchat;
    @BindView(R.id.layout_opinion)
    LinearLayout layoutOpinion;
    private Intent intent;
    private Map<String, EMConversation> conversations;
    private List<String> friendids;
    private Set<String> chatids;
    private InformDialog informDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        try {
            textNum.setText(getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    @OnClick({R.id.layout_cache, R.id.layout_temporarychat, R.id.layout_friendchat, R.id.layout_black, R.id.layout_about, R.id.button_out, R.id
            .layout_opinion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_cache://清理缓存
                informDialog = new InformDialog(SettingActivity.this, 4, R.style.MyDialog, new InformDialog
                        .LeaveMyDialogListener() {

                    @Override
                    public void onClick(View view) {

                        switch (view.getId()) {
                            case R.id.button_ok:
                                informDialog.dismiss();
                                clearAllCache(SettingActivity.this);
                                try {
                                    textNum.setText(getTotalCacheSize(SettingActivity.this));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case R.id.button_cancel:
                                informDialog.dismiss();
                                break;
                        }
                    }
                });
                informDialog.setCancelable(true);
                informDialog.show();
                break;
            case R.id.layout_temporarychat://清理临时聊天

                informDialog = new InformDialog(SettingActivity.this, 2, R.style.MyDialog, new InformDialog
                        .LeaveMyDialogListener() {

                    @Override
                    public void onClick(View view) {

                        switch (view.getId()) {
                            case R.id.button_ok:
                                informDialog.dismiss();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            friendids = EMClient.getInstance().contactManager().getAllContactsFromServer();

                                            conversations = EMClient.getInstance().chatManager().getAllConversations();
                                            chatids = conversations.keySet();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    for (String str : chatids) {
                                                        Log.i(TAG, "str: " + str);
                                                        if (!friendids.contains(str)) {
                                                            Log.i(TAG, "删除id: " + str);
                                                            //删除和某个user会话，如果需要保留聊天记录，传false
                                                            EMClient.getInstance().chatManager().deleteConversation(str, true);
                                                        }
                                                    }
                                                }
                                            });
                                        } catch (HyphenateException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();
                                break;
                            case R.id.button_cancel:
                                informDialog.dismiss();
                                break;
                        }
                    }
                });
                informDialog.setCancelable(true);
                informDialog.show();

                break;
            case R.id.layout_friendchat://清理好友聊天

                informDialog = new InformDialog(SettingActivity.this, 3, R.style.MyDialog, new InformDialog
                        .LeaveMyDialogListener() {

                    @Override
                    public void onClick(View view) {

                        switch (view.getId()) {
                            case R.id.button_ok:
                                informDialog.dismiss();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            friendids = EMClient.getInstance().contactManager().getAllContactsFromServer();

                                            conversations = EMClient.getInstance().chatManager().getAllConversations();
                                            chatids = conversations.keySet();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    for (String str : chatids) {

                                                        for (String friend : friendids) {
                                                            if (str.equals(friend)) {
                                                                Log.i(TAG, "删除id: " + str);
                                                                //删除和某个user会话，如果需要保留聊天记录，传false
                                                                EMClient.getInstance().chatManager().deleteConversation(str, true);
                                                            }
                                                        }

                                                    }
                                                }
                                            });
                                        } catch (HyphenateException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();
                                break;
                            case R.id.button_cancel:
                                informDialog.dismiss();
                                break;
                        }
                    }
                });
                informDialog.setCancelable(true);
                informDialog.show();
                break;

            case R.id.layout_black:
                intent = new Intent(this, BlackListActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_about://关于我们
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);

                break;
            case R.id.layout_opinion://意见与反馈
                intent = new Intent(this, OpinionActivity.class);
                startActivity(intent);
                break;
            case R.id.button_out:
                logout();
                break;
        }
    }

    void logout() {

        HuanXinHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        SharedPreferences preferences = getSharedPreferences("loginToken", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();

                        startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent
                                .FLAG_ACTIVITY_NEW_TASK));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                    }
                });
            }
        });
    }

}
