package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.foreseers.chat.foreseers.R;

/**
 * 我的命书信息
 *
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifebook);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        textHoroscope.setText(xingzuo);
        textZodiac.setText(zodiac);
        textZiwei.setText(ziwei);
        textNumerology.setText(numerology+"");
        textBazi4.setText(bazi[0]);
        textBazi3.setText(bazi[1]);
        textBazi2.setText(bazi[2]);
        textBazi1.setText(bazi[3]);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        xingzuo = bundle.getString("xingzuo");
        zodiac = bundle.getString("zodiac");
        ziwei = bundle.getString("ziwei");
        numerology = bundle.getInt("numerology");
        bazi = bundle.getString("bazi").split(",");
    }


    @OnClick(R.id.gotoMainPage)
    public void onViewClicked() {
     startActivity(new Intent(this,MainActivity.class));
    }
}
