package com.foreseers.chat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.foreseers.chat.R;
import com.foreseers.chat.global.MyApplication;


/**
 *
 * 设置备注
 */
public class RemarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
