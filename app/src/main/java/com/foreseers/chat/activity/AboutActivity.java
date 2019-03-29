package com.foreseers.chat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.APKVersionCodeUtils;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.text_clause) TextView textClause;
    @BindView(R.id.text_privacy) TextView textPrivacy;
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.text_versions) TextView textVersions;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        webView.loadUrl(Urls.URL + "/about_tc.html");
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
    }

    @Override
    public void initDatas() {
        textVersions.setText(getResources().getString(R.string.foreseers_version) + APKVersionCodeUtils.getVerName(this));
    }

    @Override
    public void installListeners() {
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    @OnClick({R.id.text_clause, R.id.text_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_clause:
                intent = new Intent(this, ClauseActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            case R.id.text_privacy:
                intent = new Intent(this, ClauseActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //            Log.i("ansen","拦截url:"+url);
            //            if(url.equals("http://www.google.com/")){
            //                Toast.makeText(MainActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
            //                return true;//表示我已经处理过了
            //            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();//接受证书
        }
    };
    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message)
                    .setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create()
                    .show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen", "网页标题:" + title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {}
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen", "是否有上一个页面:" + webView.canGoBack());
        //        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
        //            webView.goBack(); // goBack()表示返回webView的上一页面
        //            return true;
        //        }
        return super.onKeyDown(keyCode, event);
    }
}
