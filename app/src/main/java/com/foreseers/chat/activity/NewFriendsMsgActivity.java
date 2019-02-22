package com.foreseers.chat.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.global.BaseActivity;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewFriendsMsgActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);

        SharedPreferences sharedPreferences=getSharedPreferences("friend",MODE_PRIVATE);



//        EaseUser easeUser = new EaseUser(sharedPreferences.getString(""));
//        String info= sharedPreferences.getString(username,"");
        List<String> msgs=new ArrayList();
        Collections.reverse(msgs);
    }
}
