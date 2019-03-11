package com.foreseers.chat.global;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.foreseers.chat.bean.Constant;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.domain.InviteMessage;
import com.foreseers.chat.util.HuanXinHelper;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.util.EMLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

import static android.content.ContentValues.TAG;


/**
 * Created by EDZ on 2018/3/27.
 */

public class MyApplication extends Application {

    private InviteMessgeDao inviteMessgeDao;
    private static Context mContext;
    EMConnectionListener connectionListener;
    EMMessageListener messageListener;
    private static MyApplication instance;
    private EaseUI easeUI;
    private SharedPreferences sharedPreferences;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
        initOkGo();
        // 初始化环信SDK
        initEasemob();



    }


    private void initOkGo() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
//        //全局的读取超时时间
        builder.readTimeout(15000, TimeUnit.MILLISECONDS);
////全局的写入超时时间
//        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
////全局的连接超时时间
//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        //https相关设置，以下几种方案根据需要自己设置
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        HttpsUtils.SSLParams sslParams3 = null;
        try {
            sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("foreseers.cer"));
            builder.sslSocketFactory(sslParams3.sSLSocketFactory, sslParams3.trustManager);

            OkGo.getInstance().init(this)
                    .setOkHttpClient(builder.build());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //让Glide能用HTTPS
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory
                (builder.build()));

    }

    private void initEasemob() {
        //init demo helper
        HuanXinHelper.getInstance().init(mContext);

        // 请确保环信SDK相关方法运行在主进程，子进程不会初始化环信SDK（该逻辑在EaseUI.java中）
        if (EaseUI.getInstance().isMainProcess(this)) {
            // 初始化华为 HMS 推送服务, 需要在SDK初始化后执行
//            HMSPushHelper.getInstance().initHMSAgent(instance);
        }

    }


    public static MyApplication getInstance() {
        return instance;
    }


}
