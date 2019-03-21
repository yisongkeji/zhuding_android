package com.foreseers.chat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.foreseers.chat.activity.WipeDayHistoryActivity;
import com.foreseers.chat.adapter.WipeHistoryAdapter;
import com.foreseers.chat.bean.HistoryBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.MySection;
import com.foreseers.chat.bean.Video;
import com.foreseers.chat.decoration.GridSectionAverageGapItemDecoration;
import com.foreseers.chat.R;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class WipeHistoryFragment extends Fragment {

    private final String TAG = "WipeHistoryFragment";
    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;
    private List<List<Video>> dataBeanList = new ArrayList<>();
    private List<Video> videoslist = new ArrayList<>();
    private List<MySection> mData = new ArrayList<>();
    private LoginBean loginBean;
    private HistoryBean historyBean;
    private WipeHistoryAdapter wipeHistoryAdapter;
    private Intent intent;


    public WipeHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wipe_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        initDatas();
        return view;
    }


    public void initViews() {

        wipeHistoryAdapter = new WipeHistoryAdapter(getActivity(), mData);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 20, 10, 0));
        recycler.setAdapter(wipeHistoryAdapter);

        wipeHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection mySection = mData.get(position);
                if (mySection.isHeader) {
//                    Toast.makeText(getActivity(), mySection.header, Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(getActivity(), mySection.t.getUsername(), Toast.LENGTH_LONG).show();
                }

            }
        });
        wipeHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                .OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {//more
//                Toast.makeText(getActivity(), "more onItemChildClick" + position, Toast.LENGTH_LONG).show();
                MySection mySection = mData.get(position);
                intent = new Intent(getActivity(),WipeDayHistoryActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("datetime",mySection.header);
                getActivity().startActivity(intent);


            }
        });
    }


    public void initDatas() {
        OkGo.<String>post(Urls.Url_History).tag(this)
                .params("userid", PreferenceManager.getUserId(getActivity()))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            historyBean = gson.fromJson(response.body(), HistoryBean.class);

                            Log.i(TAG, "onSuccess: " + historyBean.getData() + "    historyBean" +
                                    ".getData().size():" + historyBean.getData().size());

                            dataBeanList = historyBean.getData();


                            for (int i = 0; i < dataBeanList.size(); i++) {
                                videoslist = dataBeanList.get(i);
                                if (videoslist.size()>0) {

                                    mData.add(new MySection(true, videoslist.get(0).getTime(),
                                            true));
                                    for (int j = 0; j < videoslist.size(); j++) {
                                        mData.add(new MySection(videoslist.get(j)));
                                        Log.e(TAG, "Data@@@@@@@@@@: " + mData.toString());

                                    }
                                }

                            }
                            Log.e(TAG, "Data: " + mData.toString());

                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();

                        }
                    }
                });
    }

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
                    Log.e(TAG, "handleMessagemData: " + mData.toString());

                    wipeHistoryAdapter.setNewData(mData);
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
