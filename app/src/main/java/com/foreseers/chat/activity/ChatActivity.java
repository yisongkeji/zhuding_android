package com.foreseers.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.foreseers.chat.R;
import com.foreseers.chat.fragment.MyChatFragment;
import com.foreseers.chat.global.BaseActivity;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;

import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity {


    private String userid;
    private String username;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void onResume() {
        super.onResume();
        EaseUI.getInstance().getNotifier().reset();
    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString(EaseConstant.EXTRA_USER_ID);
        username = bundle.getString("username");
        avatar = bundle.getString(EaseConstant.EXTRA_USER_AVATAR);


        String t_userid =getIntent().getStringExtra("userId");
        String t_nickname =getIntent().getStringExtra("nickname");
        String t_avatar =getIntent().getStringExtra("avatar");

        if (t_nickname!=null){
            userid=t_userid;
            username=t_nickname;
            avatar=t_avatar;
        }


        MyChatFragment chatFragment = new MyChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putString(EaseConstant.EXTRA_USER_ID, userid);
        args.putString(EaseConstant.EXTRA_USER_NAME, username);
        args.putString(EaseConstant.EXTRA_USER_AVATAR, avatar);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.layout, chatFragment).commit();
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
}
