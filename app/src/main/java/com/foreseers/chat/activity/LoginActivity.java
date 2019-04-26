package com.foreseers.chat.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.UserDataBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_facebook) ImageButton loginFacebook;
    @BindView(R.id.login_wechat) ImageButton loginWechat;
    @BindView(R.id.edit_login) EditText editLogin;
    @BindView(R.id.text_login) TextView textLogin;
    @BindView(R.id.check_privacy) CheckBox checkPrivacy;
    private CallbackManager callbackManager;
    private Intent intent;
    private AccessToken accessToken;
    private boolean isLoggedIn;

    private String facebookName;
    private String facebookid;
    private LoginBean loginBean;

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private int huanXinId;
    private String huanXinId1;
    private UserDataBean.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initauthority();//获取位置权限

        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        callBack();
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {

    }

    private void callBack() {
        LoginManager.getInstance()
                .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        getFacebookInfo(loginResult.getAccessToken());
                        Toast.makeText(LoginActivity.this, getActivity().getResources().getString(R.string.login_success), Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginActivity.this, getActivity().getResources().getString(R.string.login_cancel), Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(LoginActivity.this, getActivity().getResources().getString(R.string.login_defeated), Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }

    public void getFacebookInfo(AccessToken accessToken) {
        GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object != null) {
                    facebookName = object.optString("name");
                    facebookid = object.optString("id");
                    Log.e("@##@#@#@#@", "onCompleted:getFacebookInfo11111111111111111111:" + facebookid);
                    isFirst();
                    Log.e("@##@#@#@#@", "onCompleted:getFacebookInfo");
                }
            }
        })
                .executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick({R.id.login_facebook, R.id.login_wechat, R.id.text_login,R.id.check_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_login:

                facebookid = editLogin.getText()
                        .toString();
                SharedPreferences userInfo = getSharedPreferences("loginToken", MODE_PRIVATE);
                SharedPreferences.Editor editor = userInfo.edit();//获取Editor //得到Editor后，写入需要保存的数据
                editor.putString("token", facebookid);
                editor.putString("huanXinId", huanXinId + "");
                editor.commit();//提交修改

                goLogin();
                break;
            case R.id.login_facebook:
                if (checkPrivacy.isChecked()) {
                    LoginManager.getInstance()
                            .logInWithReadPermissions(this, Arrays.asList("public_profile"));
                }else {
                    Toast.makeText(this,this.getResources().getString(R.string.privacy),Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.login_wechat:

                if (editLogin.getText()
                        .length() == 0) {
                    facebookid = "467979503606542";
                    //                                    facebookid= "100002";//齐瑞宁
                    //                facebookid= "0000000000008";//刘海强
                    //                facebookid= "46797950360653";
                } else {
                    facebookid = editLogin.getText()
                            .toString();
                }

                userInfo = getSharedPreferences("loginToken", MODE_PRIVATE);
                editor = userInfo.edit();//获取Editor //得到Editor后，写入需要保存的数据
                editor.putString("token", facebookid);
                editor.putString("huanXinId", huanXinId + "");
                editor.commit();//提交修改

                goLogin();
                break;
            case R.id.check_privacy:
                intent = new Intent(this, ClauseActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }

    private void goLogin() {
        // 是否是新用户
        isFirst();
    }

    //    判断是不是新用户
    private void isFirst() {
        Log.e("@##@#@#@#@", "isFirst: ");
        OkGo.<String>post(Urls.Url_Query).tag(this)
                .params("facebookid", facebookid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);

                        if (loginBean.getStatus()
                                .equals("success")) {//老用户
                            dataBean = gson.fromJson(response.body(), UserDataBean.class)
                                    .getData();
                            huanXinId = dataBean.getId();
                            Log.e("okgo", "onSuccess: " + huanXinId);

                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
                        } else if (loginBean.getStatus()
                                .equals("fail")) {
                            //                            isLogin();
                            intent = new Intent(LoginActivity.this, UserDataActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("facebookName", facebookName);
                            bundle.putString("facebookId", facebookid);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private void loginHuanXin() {
        getHuanXinLogin();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (EMClient.getInstance()
                        .isLoggedInBefore()) {

                    EMClient.getInstance()
                            .chatManager()
                            .loadAllConversations();
                    EMClient.getInstance()
                            .groupManager()
                            .loadAllGroups();
                } else {
                    //                    goLogin();
                }
            }
        }).start();
    }

    /**
     * 判断第一次安装
     */

    public boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            preferences.edit()
                    .putBoolean("FIRSTStart", false)
                    .commit();

            Log.i("GFA", "一次");
            return true;
        } else {
            Log.i("GFA", "N次");
            return false;
        }
    }

    /**
     * 申请权限
     */
    private void initauthority() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    /**
     * 保存登录token（facebookID）
     */

    public void saveLogin(int huanXinId) {
        SharedPreferences userInfo = getSharedPreferences("loginToken", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();//获取Editor //得到Editor后，写入需要保存的数据
        editor.putString("token", facebookid);
        editor.putString("huanXinId", huanXinId + "");
        editor.commit();//提交修改
        Log.i("huanXinId", "isLogin: " + userInfo.getString("huanXinId", ""));
        SharedPreferences sharedPreferences = getSharedPreferences("condition", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.commit();
    }

    /**
     * 获取环信登录id
     */

    public void getHuanXinLogin() {
        SharedPreferences userInfo = getSharedPreferences("loginToken", MODE_PRIVATE);

        huanXinId1 = userInfo.getString("huanXinId", "");

        Log.i("huanXinId", "isLogin: " + userInfo.getString("huanXinId", ""));
    }

    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case DATASUCCESS:

                saveLogin(huanXinId);
                Log.i("EMClient", "huanXinId: " + huanXinId + "    huanXinId1: " + huanXinId1);
                EMClient.getInstance()
                        .login(huanXinId + "", "123", new EMCallBack() {//回调
                            @Override
                            public void onSuccess() {
                                EMClient.getInstance()
                                        .groupManager()
                                        .loadAllGroups();
                                EMClient.getInstance()
                                        .chatManager()
                                        .loadAllConversations();
                                Log.d("EMClient", "登录聊天服务器成功！");
                                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                sharedPreferences.edit()
                                        .putString("user", dataBean.getId() + "")
                                        .commit();
                                sharedPreferences.edit()
                                        .putString("nick", dataBean.getUsername())
                                        .commit();
                                sharedPreferences.edit()
                                        .putString("url", dataBean.getHead())
                                        .commit();
                                Log.i("SharedPreferences", "onSuccess: " + sharedPreferences.getString("url", ""));
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                Log.d("@#@#@#@#@#@#", "handleMessage: MainActivity");
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }

                            @Override
                            public void onError(int code, String message) {
                                Log.d("EMClient", "登录聊天服务器失败！");
                            }
                        });
                //                    loginHuanXin();
                //                    getHuanXinLogin();


                break;
            case DATAFELLED:

                break;
        }
    }
}
