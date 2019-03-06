package com.foreseers.chat.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.foreseers.chat.R;

public class SplashActivity extends AppCompatActivity {
    private final String TAG ="SplashActivity";
    @BindView(R.id.layout_Splash)
    LinearLayout layoutSplash;
    private String facebookid;
    private String huanXinId;
    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        ButterKnife.bind(this);

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(0);
        layoutSplash.startAnimation(animation);


    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isFirst();

            }
        }).start();


    }

    private void isFirst() {
        if (isFirstStart(this)) {// 第一次打开——》登录
            Log.i(TAG, "isFirst: 1");
            finish();
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));

        } else {//不是第一次打开——》判断是否登陆过
            getHuanXinLogin();

            if (facebookid == null || huanXinId == "") {//没登陆过
                Log.i(TAG, "isFirst: 2");
                finish();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));

            } else {//登陆过
                if (EMClient.getInstance().isLoggedInBefore()) {
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    long start = System.currentTimeMillis();
                    long costTime = System.currentTimeMillis() - start;
//                    if (sleepTime - costTime > 0) {
//                        try {
//                            Thread.sleep(sleepTime - costTime);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    Log.i(TAG, "isFirst: 3");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
//                    try {
//                        Thread.sleep(sleepTime);
//                    } catch (InterruptedException e) {
//                    }
                    Log.i(TAG, "isFirst: 4");
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();
            }

        }

    }

    /**
     * 判断第一次安装
     */

    public boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStart", false).commit();

            Log.i("GFA", "一次");
            return true;
        } else {
            Log.i("GFA", "N次");
            return false;
        }
    }

    /**
     * 获取环信登录id
     */

    public void getHuanXinLogin() {
        SharedPreferences userInfo = getSharedPreferences("loginToken", MODE_PRIVATE);

        huanXinId = userInfo.getString("huanXinId", "");
        facebookid = userInfo.getString("token", "");

        Log.i("huanXinId", "isLogin: " + userInfo.getString("huanXinId", ""));


    }
}
