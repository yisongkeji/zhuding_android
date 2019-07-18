package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.statusbarutil.SystemBarTintManager;
import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyFortuneActivity extends BaseActivity {

    @BindView(R.id.daily_fortune_back) ImageView dailyFortuneBack;
    @BindView(R.id.my_lifebook) TextView myLifebook;
    @BindView(R.id.daily_question) TextView dailyQuestion;
    @BindView(R.id.statusbar_height) TextView statusbarHeight;
    @BindView(R.id.daily_fortune_ll) LinearLayout dailyFortuneLl;
    @BindView(R.id.fortune_time) TextView fortuneTime;
    @BindView(R.id.fortune_zonghe) TextView fortuneZonghe;
    @BindView(R.id.fortune_jiankang) TextView fortuneJiankang;
    @BindView(R.id.fortune_shiye) TextView fortuneShiye;
    @BindView(R.id.fortune_love) TextView fortuneLove;
    @BindView(R.id.fortune_money) TextView fortuneMoney;
    @BindView(R.id.fortune_interpersonal) TextView fortuneInterpersonal;

    private String lifeuserid;
    private String name;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_fortune);
        ButterKnife.bind(this);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) statusbarHeight.getLayoutParams();
        layoutParams.height = SystemBarTintManager.getStatusBarHeight(DailyFortuneActivity.this);
        statusbarHeight.setBackgroundColor(ContextCompat.getColor(DailyFortuneActivity.this, R.color.colorAccent));
        statusbarHeight.setLayoutParams(layoutParams);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    public void initDatas() {
        lifeuserid = getIntent().getStringExtra("lifeuserid");
        name = getIntent().getStringExtra("name");

        OkGo.<String>post(Urls.Url_DailyFortune).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            if (jsonObject.getString("status")
                                    .equals("success")) {
                                JSONObject jsonData = new JSONObject(jsonObject.getString("data"));
                                fortuneTime.setText(jsonData.getString("date"));
                                fortuneLove.setText(jsonData.getString("love"));
                                fortuneMoney.setText(jsonData.getString("money"));
                                fortuneShiye.setText(jsonData.getString("job"));
                                fortuneZonghe.setText(jsonData.getString("fortune"));
                                fortuneJiankang.setText(jsonData.getString("health"));
                                fortuneInterpersonal.setText(jsonData.getString("interpersonal"));
                            }
                            hud.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        hud.dismiss();
                    }

                });
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    @OnClick({R.id.daily_fortune_back, R.id.my_lifebook, R.id.daily_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.daily_fortune_back:
                finish();
                break;
            case R.id.my_lifebook:
                if (!TextUtils.isEmpty(lifeuserid) && !TextUtils.isEmpty(name)) {
                    startActivity(new Intent(DailyFortuneActivity.this, FortunetellingOutlineActivity.class).putExtra("lifeuserid", lifeuserid)
                                          .putExtra("name", name));
                }
                break;
            case R.id.daily_question:
                startActivity(new Intent(DailyFortuneActivity.this, DailyQuestionActivity.class));
                break;
        }
    }
}
