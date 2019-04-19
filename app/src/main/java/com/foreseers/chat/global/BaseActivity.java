package com.foreseers.chat.global;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.com.statusbarutil.StatusBarUtil;
import com.foreseers.chat.R;
import com.foreseers.chat.interf.IBaseActivity;

import butterknife.internal.Utils;

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    public final int DATASUCCESS = 1;
    public final int DATAFELLED = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(this, true);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
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
