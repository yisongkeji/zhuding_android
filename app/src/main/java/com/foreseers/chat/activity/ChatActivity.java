package com.foreseers.chat.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cy.translucentparent.StatusNavUtils;
import com.foreseers.chat.fragment.MyChatFragment;
import com.foreseers.chat.global.BaseActivity;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.ButterKnife;

import com.foreseers.chat.foreseers.R;

public class ChatActivity extends AppCompatActivity {


    private String userid;
    private String username;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);




        StatusNavUtils.setStatusBarColor(this,0x00000000);
//        Window window = getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
// WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
// View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(0x00000000);
//            return;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//        }

        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString(EaseConstant.EXTRA_USER_ID);
        username = bundle.getString("username");
        avatar = bundle.getString(EaseConstant.EXTRA_USER_AVATAR);


        Log.i("chatActivity", "userid" + userid + "````username" + username);
        initView();
    }

    private void initView() {
        MyChatFragment chatFragment = new MyChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putString(EaseConstant.EXTRA_USER_ID, userid);
        args.putString(EaseConstant.EXTRA_USER_NAME, username);
        args.putString(EaseConstant.EXTRA_USER_AVATAR, avatar);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.layout, chatFragment).commit();
    }


}
