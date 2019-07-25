package com.foreseers.chat.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.foreseers.chat.R;
import com.foreseers.chat.activity.ReportActivity;
import com.foreseers.chat.adapter.AttentionAdapter;
import com.foreseers.chat.bean.AttentionData;
import com.foreseers.chat.global.BaseFragment;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * File description.
 *
 * @author how
 * @date 2019/7/8
 */
public class AttentionFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.attention_rv) RecyclerView attentionRv;
    @BindView(R.id.swipeLayout) SwipeRefreshLayout swipeLayout;

    private AttentionAdapter attentionAdapter;
    private View view;
    private List<AttentionData> datas = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_attention, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initViews() {
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                }}));
            } else if (i == 1) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190530110309860607.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                }}));
            } else if (i == 2) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                }}));
            } else if (i == 3) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/1557200437155.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                }}));
            } else if (i == 4) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/1557200437155.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                }}));
            } else if (i == 5) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/1557200437155.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                }}));
            } else if (i == 6) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/1557200437155.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                }}));
            } else if (i == 7) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/1557200437155.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                }}));
            } else if (i == 8) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110316901618.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/1557200437155.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/20190426141605608335.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                }}));
            } else if (i == 9) {
                datas.add(new AttentionData(new ArrayList<String>() {{
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                    add("https://chat.foreseers.cn:443/100/20190530110327410729.jpg");
                }}));
            }
        }
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        attentionAdapter = new AttentionAdapter(getContext());
        attentionRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        attentionRv.setHasFixedSize(true);
        attentionRv.setAdapter(attentionAdapter);
        attentionAdapter.setNewData(datas);
//        View view=getActivity().getLayoutInflater().inflate(R.layout.ease_search_bar_with_padding,null);
//        attentionAdapter.setHeaderView(view);
        attentionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //投诉
                    case R.id.item_stargroup_share:
                        initPopupWind();
                        break;
                    //红心
                    case R.id.item_stargroup_praise:
                        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                        attentionAdapter.notifyItem(1, position);
                        break;
                    //评论
                    case R.id.item_stargroup_volume:
                        attentionAdapter.notifyItem(2, position);
                        break;
                    //分享
                    case R.id.item_stargroup_sharecount:
                        attentionAdapter.notifyItem(3, position);
                        break;
                    default:
                        break;
                }
            }
        });
        //        attentionAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
        //            @Override
        //            public void onLoadMoreRequested() {
        //
        //            }
        //        },attentionRv);
    }

    @Override
    public void initDatas() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    //弹出举报dialog
    private void initPopupWind() {

        new PopWindow.Builder(getActivity()).setStyle(PopWindow.PopWindowStyle.PopUp)
                .addItemAction(new PopItemAction(getActivity().getResources()
                                                         .getString(R.string.foreseers_inform), PopItemAction.PopItemStyle.Normal,
                                                 new PopItemAction.OnClickListener() {
                                                     @Override
                                                     public void onClick() {
                                                         //举报
                                                         Intent intent = new Intent(getActivity(), ReportActivity.class);
                                                         startActivity(intent);
                                                     }
                                                 }))
                .addItemAction(new PopItemAction(getActivity().getResources()
                                                         .getString(R.string.not_interest), PopItemAction.PopItemStyle.Normal,
                                                 new PopItemAction.OnClickListener() {
                                                     @Override
                                                     public void onClick() {
                                                         //不感兴趣
                                                     }
                                                 }))
                .addItemAction(new PopItemAction(getActivity().getResources()
                                                         .getString(R.string.cancel), PopItemAction.PopItemStyle.Cancel,
                                                 new PopItemAction.OnClickListener() {
                                                     @Override
                                                     public void onClick() {

                                                     }
                                                 }))
                .create()
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
