package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.foreseers.chat.R;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.UserDataBean;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * 我的命书信息
 */
public class LifeBookActivity extends AppCompatActivity {


    @BindView(R.id.text_horoscope)
    TextView textHoroscope;
    @BindView(R.id.text_zodiac)
    TextView textZodiac;
    @BindView(R.id.text_ziwei)
    TextView textZiwei;
    @BindView(R.id.text_numerology)
    TextView textNumerology;
    @BindView(R.id.text_bazi4)
    TextView textBazi4;
    @BindView(R.id.text_bazi3)
    TextView textBazi3;
    @BindView(R.id.text_bazi2)
    TextView textBazi2;
    @BindView(R.id.text_bazi1)
    TextView textBazi1;
    @BindView(R.id.gotoMainPage)
    TextView gotoMainPage;

    private String xingzuo;

    private String zodiac;
    private String ziwei;
    private int numerology;
    private String[] bazi;
    private UserDataBean.DataBean dataBean;
    private UserDataBean userDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifebook);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            gotoMainPage.setVisibility(View.GONE);
        }else {
            gotoMainPage.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {


        OkGo.<String>post(Urls.Url_Query).tag(this)
                .params("facebookid", PreferenceManager.getFaceBookId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {//老用户

                            userDataBean = gson.fromJson(response.body(), UserDataBean.class);

                            dataBean = userDataBean.getData();

                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();


                        } else if (loginBean.getStatus().equals("fail")) {

                        }
                    }
                });

    }

    private final int DATASUCCESS = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    if (textBazi1 != null) {
                        xingzuo = dataBean.getXingzuo();
                        zodiac = dataBean.getZodiac();
                        ziwei = dataBean.getZiwei();
                        numerology = dataBean.getNumerology();
                        bazi = dataBean.getBazi().split(",");


                        textHoroscope.setText(xingzuo);
                        textZodiac.setText(zodiac);
                        textZiwei.setText(ziwei);
                        textNumerology.setText(numerology + "");
                        textBazi4.setText(bazi[0]);
                        textBazi3.setText(bazi[1]);
                        textBazi2.setText(bazi[2]);
                        textBazi1.setText(bazi[3]);
                    }


                    break;

            }
        }
    };

    @OnClick(R.id.gotoMainPage)
    public void onViewClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
