package com.foreseers.chat.foreseers.global;

import android.app.Application;
import android.content.Context;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.lzy.okgo.OkGo;


/**
 * Created by EDZ on 2018/3/27.
 */

public class MyApplication extends Application {


    private static Context mContext;
    EMConnectionListener connectionListener;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        initOkGo();
        // 初始化环信SDK
        initEasemob();

    }


    private void initOkGo() {
        OkGo.getInstance().init(this);

    }

    private void initEasemob() {
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(true);
        options.setAutoLogin(true);

        EaseUI.getInstance().init(this, options);


    }




}
