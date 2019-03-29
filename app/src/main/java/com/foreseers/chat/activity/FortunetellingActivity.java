package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.foreseers.chat.R;
import com.foreseers.chat.adapter.FortunetellingAdapter;
import com.foreseers.chat.bean.FortunetellingBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 命书测算
 */
public class FortunetellingActivity extends BaseActivity {

    private String TAG="FortunetellingActivity";
    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    private Intent intent;
    private List<Integer> rgblist = new ArrayList();
    private FortunetellingBean fortunetellingBean;
    private List<FortunetellingBean.DataBean> dataBeanList = new ArrayList<>();
    private FortunetellingAdapter fortunetellingAdapter;

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
        setContentView(R.layout.activity_fortunetelling);
        ButterKnife.bind(this);
        rgblist.add(this.getResources().getColor(R.color.colorLisst1));
        rgblist.add(this.getResources().getColor(R.color.colorLisst2));
        rgblist.add(this.getResources().getColor(R.color.colorLisst3));
        rgblist.add(this.getResources().getColor(R.color.colorLisst4));
        rgblist.add(this.getResources().getColor(R.color.colorLisst5));
        rgblist.add(this.getResources().getColor(R.color.colorLisst6));
        rgblist.add(this.getResources().getColor(R.color.colorBlue));
        rgblist.add(this.getResources().getColor(R.color.colorRed));
        rgblist.add(this.getResources().getColor(R.color.colorOrange));
        rgblist.add(this.getResources().getColor(R.color.colorGreen));
        rgblist.add(this.getResources().getColor(R.color.colorMain));

        fortunetellingAdapter = new FortunetellingAdapter(this, dataBeanList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(fortunetellingAdapter);
    }

    @Override
    public void initDatas() {

        OkGo.<String>post(Urls.Url_LifeBookCate).tag(this)
                .params("lifeuserid", getIntent().getIntExtra("lifeuserid", 0))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            fortunetellingBean = gson.fromJson(response.body(), FortunetellingBean.class);
                            dataBeanList = fortunetellingBean.getData();
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

                for (int i = 0; i < dataBeanList.size(); i++) {
                    for (int j = 0; j < rgblist.size(); j++) {
                        if (j==i){
                            dataBeanList.get(i).setColour(rgblist.get(j));
                        }
                    }
                }
                fortunetellingAdapter.setNewData(dataBeanList);
                break;
        }
    }
}
