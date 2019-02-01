package com.foreseers.chat.global;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cy.translucentparent.StatusNavUtils;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusNavUtils.setStatusBarColor(this,0x00000000);
    }

}
