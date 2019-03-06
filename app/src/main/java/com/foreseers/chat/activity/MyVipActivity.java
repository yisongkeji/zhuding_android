package com.foreseers.chat.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.ruffian.library.widget.RImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyVipActivity extends BaseActivity {


    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.layout_vip1)
    FrameLayout layoutVip1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.layout_vip2)
    LinearLayout layoutVip2;
    @BindView(R.id.layout_vip3)
    LinearLayout layoutVip3;
    @BindView(R.id.rimg_head)
    RImageView rimgHead;
    @BindView(R.id.text_username)
    TextView textUsername;
    @BindView(R.id.text_day)
    TextView textDay;
    private String head;
    private String day;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vip);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        head = bundle.getString("head", "");
        day = bundle.getString("day", "");
        name = bundle.getString("name", "");

    }

    private void initView() {
        Glide.with(this).load(head).into(rimgHead);
        textUsername.setText(name);
        textDay.setText(day);

        text1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        text2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }
}
