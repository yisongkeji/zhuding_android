package com.foreseers.chat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.cy.translucentparent.StatusNavUtils;
import com.foreseers.chat.foreseers.R;

public class MyVipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vip);
        StatusNavUtils.setStatusBarColor(this, 0x00000000);
    }
}
