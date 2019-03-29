package com.foreseers.chat.global;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.cy.translucentparent.StatusNavUtils;
import com.foreseers.chat.interf.IBaseActivity;

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    public final int DATASUCCESS = 1;
    public final int DATAFELLED = 0;
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
