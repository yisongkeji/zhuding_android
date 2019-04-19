package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.foreseers.chat.R;
import com.foreseers.chat.adapter.FortunetellingListAdapter;
import com.foreseers.chat.bean.FortunetellingBean;
import com.foreseers.chat.bean.FortunetellingListBean;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *算命详细清单
 */
public class FortunetellingListActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    private FortunetellingListBean fortunetellingListBean;
    private List<String> dataBeans = new ArrayList<>();
    private FortunetellingListAdapter fortunetellingListAdapter;
    private String name;
    private String lifeuserid;

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
        setContentView(R.layout.activity_fortunetelling_list);
        ButterKnife.bind(this);
        fortunetellingListAdapter = new FortunetellingListAdapter(this, dataBeans);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(fortunetellingListAdapter);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        lifeuserid = bundle.getString("lifeuserid");
        myTitlebar.setTitle(name);
    }

    @Override
    public void initDatas() {

        OkGo.<String>post(Urls.Url_LifeBookDetail).tag(this)
                .params("name", name)
                .params("lifeuserid", lifeuserid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            fortunetellingListBean = gson.fromJson(response.body(), FortunetellingListBean.class);

                            dataBeans = fortunetellingListBean.getData();

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
        fortunetellingListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(FortunetellingListActivity.this,FortunetellingContentActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("lifeuserid",lifeuserid);
                bundle.putString("name",name);
                bundle.putString("title", dataBeans.get(position));
               intent.putExtras(bundle);
               startActivity(intent);
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

        switch (msg.what) {
            case DATASUCCESS:
                if (recyclerview != null) {

                    fortunetellingListAdapter.setNewData(dataBeans);
                }
                break;
        }
    }
}
