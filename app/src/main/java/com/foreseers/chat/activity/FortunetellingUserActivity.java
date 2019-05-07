package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.foreseers.chat.R;
import com.foreseers.chat.adapter.FortunetellingUserAdapter;
import com.foreseers.chat.bean.LifeBookUserBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 命书测算
 */
public class FortunetellingUserActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.layout_item1) LinearLayout layoutItem1;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.layout_item3) LinearLayout layoutItem3;
    private FortunetellingUserAdapter fortunetellingUserAdapter;
    private LifeBookUserBean lifeBookUserBean;
    private List<LifeBookUserBean.DataBean> lifeBookUserBeanList = new ArrayList<>();
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_fortunetelling_user);
        ButterKnife.bind(this);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        fortunetellingUserAdapter = new FortunetellingUserAdapter(FortunetellingUserActivity.this, lifeBookUserBeanList);
        recyclerview.setAdapter(fortunetellingUserAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fortunetellingUserAdapter.expandAll();
    }

    @Override
    public void initDatas() {
        OkGo.<String>post(Urls.Url_lifeUser).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            lifeBookUserBean = gson.fromJson(response.body(), LifeBookUserBean.class);
                            lifeBookUserBeanList = lifeBookUserBean.getData();
                            getHandler().obtainMessage(DATASUCCESS).sendToTarget();
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
                fortunetellingUserAdapter.setNewData(lifeBookUserBeanList);
                hud.dismiss();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.layout_item1, R.id.layout_item3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_item1:
                if (recyclerview.getVisibility() == View.VISIBLE) {
                    recyclerview.setVisibility(View.GONE);
                } else {
                    recyclerview.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.layout_item3:
                Intent intent = new Intent(FortunetellingUserActivity.this, FortunetellingDataActivity.class);
                startActivity(intent);
                break;
        }
    }
}
