package chat.foreseers.com.foreseers.activity;

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
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.bean.LoginBean;
import chat.foreseers.com.foreseers.util.Urls;


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
    private String facebookId = "";
    private String imgUrl = "";
    private String email;
    private LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        判断是否第一次打开
        if (isFirstStart(this)){
//            第一次打开——》facebook登录
            init();
            callBack();
        }else {
//            不是第一次登录——》MainActivity
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



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

        GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object != null) {
                    Log.i("@@@@@@@@", "onCompleted: " + object.toString() + "####========" +
                            object.optString("email"));
                    facebookName = object.optString("name");
                    facebookId = object.optString("id");
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
//        是否是新用户
        isFirst();

        isLogin();

    }

//    判断是不是新用户
    private void isFirst() {
        OkGo.<String>post(Urls.Url_Login).tag(this)
                .params("facebookid",facebookId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson=new Gson();
                        loginBean = gson.fromJson(response.body(),LoginBean.class);
                        if (loginBean.getStatus().equals("success")){
                            intent =new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            intent =new Intent(LoginActivity.this,UserDataActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("facebookName", facebookName);
                            bundle.putString("facebookId", facebookId);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
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

//            intent = new Intent(LoginActivity.this, SplashActivity.class);
//            startActivity(intent);
//            finish();
            Log.i("GFA", "一次");
            return true;
        } else {

            Log.i("GFA", "N次");
            return false;
        }
    }

    /**
     * 保存登录token（facebookID）
     */

    public void isLogin(){
        SharedPreferences userInfo = getSharedPreferences("loginToken", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();//获取Editor //得到Editor后，写入需要保存的数据
        editor.putString("token", facebookId);
        editor.commit();//提交修改

    }

}
