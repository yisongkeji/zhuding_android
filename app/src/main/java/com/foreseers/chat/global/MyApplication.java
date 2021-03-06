package com.foreseers.chat.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.util.HuanXinHelper;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.easeui.EaseUI;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

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

    private static Typeface typeface;
    private static String lang;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        instance = this;

                initOkGo();//手动配置
//        OkGo.getInstance().init(this);//默认

        // 初始化环信SDK
        initEasemob();
        MultiDex.install(mContext);
        //设置字体
        //        initTypeFace();
    }

    private void initlog() {
        //        refWatcher = setupLeakCanary();//2

        //        if (LeakCanary.isInAnalyzerProcess(this)) {
        //            return;
        //        }
        //        LeakCanary.install(this);
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

        //https相关设置，
        //信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        //使用预埋证书，校验服务端证书（自签名证书）
        //        HttpsUtils.SSLParams sslParams3 = null;
        //        try {
        //            sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("foreseers.cer"));
        //            builder.sslSocketFactory(sslParams3.sSLSocketFactory, sslParams3.trustManager);
        //
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }

        //配置https的域名匹配规则
        builder.hostnameVerifier(new SafeHostnameVerifier());

        OkGo.getInstance()
                .init(this)
                .setOkHttpClient(builder.build());
        //        让Glide能用HTTPS
        Glide.get(this)
                .register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(builder.build()));
    }

    private void initEasemob() {
        //init demo helper
        HuanXinHelper.getInstance()
                .init(mContext);

        // 请确保环信SDK相关方法运行在主进程，子进程不会初始化环信SDK（该逻辑在EaseUI.java中）
        if (EaseUI.getInstance()
                .isMainProcess(this)) {
            // 初始化华为 HMS 推送服务, 需要在SDK初始化后执行
            //            HMSPushHelper.getInstance().initHMSAgent(instance);
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static String getlang() {return lang;}

    private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //验证主机名是否匹配
            //            return hostname.equals("192.168.1.73");
            return true;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initTypeFace() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration()
                    .getLocales()
                    .get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }
        lang = locale.getLanguage() + "-" + locale.getCountry();

        Log.i("&*&*&*&", "initTypeFace: " + lang);

        //        //        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/华康华综体W5-B5.TTF");
        //        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/汉鼎繁中圆.TTF");
        //        Field field = null;
        //        try {
        //            field = Typeface.class.getDeclaredField("SERIF");
        //            field.setAccessible(true);
        //            field.set(null, typeface);
        //        } catch (NoSuchFieldException e) {
        //            e.printStackTrace();
        //        } catch (IllegalAccessException e) {
        //            e.printStackTrace();
        //        }
    }

    //    private RefWatcher setupLeakCanary() {
    //        if (LeakCanary.isInAnalyzerProcess(this)) {
    //            return RefWatcher.DISABLED;
    //        }
    //        return LeakCanary.install(this);//1
    //    }
    //
    //    public static RefWatcher getRefWatcher(Context context) {
    //        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
    //        return leakApplication.refWatcher;
    //    }
}
