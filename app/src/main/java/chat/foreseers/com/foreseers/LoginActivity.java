package chat.foreseers.com.foreseers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_facebook)
    ImageButton loginFacebook;
    @BindView(R.id.login_wechat)
    ImageButton loginWechat;
    private CallbackManager callbackManager;
    private Intent intent;
    private AccessToken accessToken;
    private boolean isLoggedIn;


    private String userId = "";
    private String facebookName = "";
    private String imgUrl = "";
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
        callBack();


    }

    private void init() {
        callbackManager = CallbackManager.Factory.create();
//        loginButton.setReadPermissions("user_friends, email");

        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

    }

    private void callBack() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        getFacebookInfo(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginActivity.this, "取消登錄", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(LoginActivity.this, "登錄失敗", Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void getFacebookInfo(AccessToken accessToken) {
        userId = accessToken.getUserId();
        GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object != null) {
                    Log.i("@@@@@@@@", "onCompleted: " + object.toString() + "####========" +
                            object.optString("email"));
                    facebookName = object.optString("name");
                    email = object.optString("email");
                    goLogin();
//                    isFirstStart(getApplicationContext());
                }
            }
        }).executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //, "user_friends","email","user_birthday"
    @OnClick({R.id.login_facebook, R.id.login_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_facebook:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList
                        ("public_profile"));
                break;
            case R.id.login_wechat:
                break;
        }
    }

    private void goLogin() {
        Toast.makeText(LoginActivity.this, "登錄成功", Toast
                .LENGTH_LONG).show();

        intent = new Intent(LoginActivity.this, UserDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("facebookName", facebookName);
        intent.putExtras(bundle);
        startActivity(intent);
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

            intent = new Intent(LoginActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
            Log.i("GFA", "一次");
            return true;
        } else {
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Log.i("GFA", "N次");
            return false;
        }
    }


}
