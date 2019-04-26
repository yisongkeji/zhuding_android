package com.foreseers.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.foreseers.chat.R;
import com.foreseers.chat.adapter.BlackListAdapter;
import com.foreseers.chat.bean.BlackBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.dialog.InformDialog;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlackListActivity extends BaseActivity {
    private final int DATASUCCESS = 1;
    private final int DATASUCCESS2 = 2;

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    private List<String> list = new ArrayList<>();
    private BlackBean blackBean;
    private List<BlackBean.DataBean> dataBeans = new ArrayList<>();
    private BlackListAdapter blackListAdapter;
    private InformDialog informDialog;

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
        setContentView(R.layout.activity_black_list);
        ButterKnife.bind(this);

        blackListAdapter = new BlackListAdapter(this, dataBeans);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(blackListAdapter);
    }

    @Override
    public void initDatas() {
        getDataFromHttp();
    }

    @Override
    public void installListeners() {
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        blackListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final BlackBean.DataBean item = dataBeans.get(position);
                new PopWindow.Builder(BlackListActivity.this).setStyle(PopWindow.PopWindowStyle.PopUp)
                        .addItemAction(new PopItemAction(getActivity().getResources().getString(R.string.removed_from_blacklist), PopItemAction.PopItemStyle.Normal, new
                                PopItemAction
                                .OnClickListener() {
                            @Override
                            public void onClick() {

                                informDialog = new InformDialog(BlackListActivity.this, 1, R.style.MyDialog,
                                                                new InformDialog.LeaveMyDialogListener() {

                                                                    @Override
                                                                    public void onClick(View view) {

                                                                        switch (view.getId()) {
                                                                            case R.id.button_ok:
                                                                                informDialog.dismiss();
                                                                                new Thread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        try {
                                                                                            EMClient.getInstance()
                                                                                                    .contactManager()
                                                                                                    .removeUserFromBlackList(item.getUserid() + "");
                                                                                        } catch (HyphenateException e) {
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                    }
                                                                                }).start();

                                                                                OkGo.<String>post(Urls.Url_RemoveBlack).tag(this)
                                                                                        .params("userid", PreferenceManager.getUserId(BlackListActivity.this))
                                                                                        .params("blackid", item.getUserid() + "")
                                                                                        .execute(new StringCallback() {
                                                                                            @Override
                                                                                            public void onSuccess(Response<String> response) {
                                                                                                getDataFromHttp();
                                                                                            }
                                                                                        });

                                                                                break;
                                                                            case R.id.button_cancel:
                                                                                informDialog.dismiss();
                                                                                break;
                                                                        }
                                                                    }
                                                                });
                                informDialog.setCancelable(true);
                                informDialog.show();
                            }
                        }))
                        .addItemAction(new PopItemAction(getActivity().getResources().getString(R.string.cancel), PopItemAction.PopItemStyle.Cancel, new
                                PopItemAction
                                .OnClickListener() {
                            @Override
                            public void onClick() {

                            }
                        }))
                        .create()
                        .show();
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

        switch (msg.what) {
            case DATASUCCESS:
                if (recyclerview != null) {

                    blackListAdapter.setNewData(dataBeans);
                }
                break;
        }
    }

    private void getDataFromHttp() {
        OkGo.<String>post(Urls.Url_BlackList).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            blackBean = gson.fromJson(response.body(), BlackBean.class);
                            dataBeans = blackBean.getData();
                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
                        }
                    }
                });
    }
}
