package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.statusbarutil.SystemBarTintManager;
import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyFortuneActivity extends BaseActivity {

    @BindView(R.id.daily_fortune_back) ImageView dailyFortuneBack;
    @BindView(R.id.my_lifebook) TextView myLifebook;
    @BindView(R.id.daily_question) TextView dailyQuestion;
    @BindView(R.id.statusbar_height) TextView statusbarHeight;
    @BindView(R.id.daily_fortune_ll) LinearLayout dailyFortuneLl;

    private String lifeuserid;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_fortune);
        ButterKnife.bind(this);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) statusbarHeight.getLayoutParams();
        layoutParams.height = SystemBarTintManager.getStatusBarHeight(DailyFortuneActivity.this);
        statusbarHeight.setBackgroundColor(ContextCompat.getColor(DailyFortuneActivity.this,R.color.colorAccent));
        statusbarHeight.setLayoutParams(layoutParams);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {
        lifeuserid = getIntent().getStringExtra("lifeuserid");
        name = getIntent().getStringExtra("name");
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
