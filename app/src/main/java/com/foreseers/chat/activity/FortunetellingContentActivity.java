package com.foreseers.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.FortunetellingContentBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FortunetellingContentActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.text_content) TextView textContent;
    @BindView(R.id.img) ImageView img;
    private Bundle bundle;
    private FortunetellingContentBean dataBean;
    private List<FortunetellingContentBean.DataBean> dataBeanList = new ArrayList<>();

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
        setContentView(R.layout.activity_fortunetelling_content);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        myTitlebar.setTitle(bundle.getString("title"));
    }

    @Override
    public void initDatas() {

        OkGo.<String>post(Urls.Url_LifeBookDetailname).tag(this)
                .params("lifesuerid", bundle.getString("lifesuerid"))
                .params("name", bundle.getString("name"))
                .params("title", bundle.getString("title"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            dataBean = gson.fromJson(response.body(), FortunetellingContentBean.class);
                            dataBeanList = dataBean.getData();
                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
                        }
                    }
                });
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

        switch (msg.what) {
            case DATASUCCESS:
                Glide.with(this).load(dataBeanList.get(0).getIcon()).into(img);
//                GlideUtil.glide(this,dataBeanList.get(0).getIcon(),img);
                textContent.setText(Html.fromHtml(dataBeanList.get(0).getComment()));

                break;
        }
    }
}
