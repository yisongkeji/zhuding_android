package com.foreseers.chat.global;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cy.translucentparent.StatusNavUtils;
import com.foreseers.chat.interf.IBaseActivity;
import com.foreseers.chat.interf.IBaseFragment;

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {


    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        StatusNavUtils.setStatusBarColor(this,0x00000000);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                processHandlerMessage(msg);
            }
        };
        initViews();
        initDatas();
        installListeners();
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }


}
