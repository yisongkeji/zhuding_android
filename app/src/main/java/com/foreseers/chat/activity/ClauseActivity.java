package com.foreseers.chat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.foreseers.chat.R;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClauseActivity extends AppCompatActivity {

    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clause);
        ButterKnife.bind(this);
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webView.loadUrl(Urls.URL+"/tnc_tc.html");

    }
}
