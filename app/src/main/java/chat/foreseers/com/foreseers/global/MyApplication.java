package chat.foreseers.com.foreseers.global;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * Created by EDZ on 2018/3/27.
 */

public class MyApplication extends Application {


    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);

        mContext = getApplicationContext();
        initOkGo();

    }

    private void initOkGo() {
        OkGo.getInstance().init(this);

    }


}
