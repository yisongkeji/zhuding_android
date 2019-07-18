package com.foreseers.chat.global;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.com.statusbarutil.StatusBarUtil;
import com.foreseers.chat.interf.IBaseActivity;

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {

    public final int DATASUCCESS = 1;
    public final int DATAFELLED = 0;
    private Handler mHandler;
    protected InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
        hideSoftKeyboard();
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    public void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
