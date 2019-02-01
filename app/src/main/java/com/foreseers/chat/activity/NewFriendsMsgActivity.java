package com.foreseers.chat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.foreseers.chat.foreseers.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewFriendsMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);
        List<String> msgs=new ArrayList();
        Collections.reverse(msgs);
    }
}
