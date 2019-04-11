package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.foreseers.chat.R;
import com.foreseers.chat.adapter.FortunetellingUserAdapter;
import com.foreseers.chat.bean.LifeBookUserBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.item.Level0Item;
import com.foreseers.chat.view.item.Level1Item;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
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
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.view_item3) View viewItem3;
    @BindView(R.id.text_item3) TextView textItem3;
    @BindView(R.id.layout_item3) LinearLayout layoutItem3;
    private FortunetellingUserAdapter fortunetellingUserAdapter;
    private Level0Item lv0;
    private Level1Item lv1;
    private LifeBookUserBean lifeBookUserBean;
    private List<LifeBookUserBean.DataBean> lifeBookUserBeanList = new ArrayList<>();
    ArrayList<MultiItemEntity> list;

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
        setContentView(R.layout.activity_fortunetelling_user);
        ButterKnife.bind(this);
        list = generateData();
        fortunetellingUserAdapter = new FortunetellingUserAdapter(this, list);

        //        view_item2.setBackgroundResource(R.color.colorOrange);
        //        textTitle_item2.setText(getResources().getString(R.string.txt_shop_lifebook_item2));
        viewItem3.setBackgroundResource(R.color.colorBlue2);
        textItem3.setText(getResources().getString(R.string.txt_shop_lifebook_item3));

        recyclerview.setAdapter(fortunetellingUserAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fortunetellingUserAdapter.expandAll();
    }

    @Override
    public void initDatas() {

    }

    private void initData(){
        OkGo.<String>post(Urls.Url_lifeUser).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {

                            lifeBookUserBean = gson.fromJson(response.body(), LifeBookUserBean.class);
                            lifeBookUserBeanList = lifeBookUserBean.getData();
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
                list = generateData();
                fortunetellingUserAdapter.setNewData(list);
                fortunetellingUserAdapter.expandAll();
                break;
        }
    }

    private ArrayList<MultiItemEntity> generateData() {

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        lv0 = new Level0Item(getResources().getString(R.string.txt_shop_lifebook_item2), R.color.colorOrange);
        if (lifeBookUserBeanList.size() > 0) {
            for (int j = 0; j < lifeBookUserBeanList.size(); j++) {
                lv1 = new Level1Item(lifeBookUserBeanList.get(j)
                                             .getName(), lifeBookUserBeanList.get(j)
                                             .getDate() + " " + lifeBookUserBeanList.get(j)
                        .getTime(), lifeBookUserBeanList.get(j)
                                             .getLifeuserid());
                lv0.addSubItem(lv1);
            }
        }
        res.add(lv0);
        return res;
    }

    @OnClick(R.id.layout_item3)
    public void onViewClicked() {
        Intent intent = new Intent(this, FortunetellingDataActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
