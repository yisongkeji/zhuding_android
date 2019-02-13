package com.foreseers.chat.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.view.widget.MyTitleBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vip);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        text1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        text2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
