package chat.foreseers.com.foreseers.global;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.logging.Level;

import chat.foreseers.com.foreseers.activity.MainActivity;
import okhttp3.OkHttpClient;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;


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
