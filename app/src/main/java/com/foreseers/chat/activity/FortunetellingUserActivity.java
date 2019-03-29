package com.foreseers.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.facebook.login.Login;
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

public class FortunetellingUserActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    ArrayList<MultiItemEntity> list;
    private FortunetellingUserAdapter fortunetellingUserAdapter;
    private Level0Item lv0;
    private Level1Item lv1;
    private LifeBookUserBean lifeBookUserBean;
    private List<LifeBookUserBean.DataBean> lifeBookUserBeanList=new ArrayList<>();

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
        fortunetellingUserAdapter = new FortunetellingUserAdapter(this,list);

        recyclerview.setAdapter(fortunetellingUserAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        fortunetellingUserAdapter.expandAll();
    }

    @Override
    public void initDatas() {
        OkGo.<String>post(Urls.Url_lifeUser).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson=new Gson();
                        LoginBean loginBean=gson.fromJson(response.body(),LoginBean.class);
                        if (loginBean.getStatus().equals("success")){

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
        switch (msg.what){
            case DATASUCCESS:
                list=generateData();
                fortunetellingUserAdapter.setNewData(list);
                fortunetellingUserAdapter.expandAll();
                break;
        }

    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 3;


        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            if (i == 0) {
                lv0 = new Level0Item(getResources().getString(R.string.txt_shop_lifebook_item1), R.color.colorAccent);
                lv1 = new Level1Item("刘亦菲", "182.168.0.1",0);
                lv0.addSubItem(lv1);
            } else if (i == 1) {
                lv0 = new Level0Item(getResources().getString(R.string.txt_shop_lifebook_item2), R.color.colorOrange);
                if (lifeBookUserBeanList.size()>0){
                    for (int j = 0; j < lifeBookUserBeanList.size(); j++) {
                        lv1 = new Level1Item(lifeBookUserBeanList.get(j).getName(),lifeBookUserBeanList.get(j).getDate()+" "+lifeBookUserBeanList.get(j)
                                .getTime(),lifeBookUserBeanList.get(j).getLifeuserid());
                        lv0.addSubItem(lv1);
                    }
                }

            } else if (i == 2) {
                lv0 = new Level0Item(getResources().getString(R.string.txt_shop_lifebook_item3), R.color.colorBlue2);
//                lv1 = new Level1Item("刘亦菲222" , "182.168.0.1");
//                lv0.addSubItem(lv1);
            }

            res.add(lv0);
        }
        //        res.add(new Level0Item("This is " + lv0Count + "th item in Level 0", R.color.colorBlue));
        return res;
    }
}
