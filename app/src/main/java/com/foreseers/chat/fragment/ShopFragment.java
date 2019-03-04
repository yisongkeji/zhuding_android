package com.foreseers.chat.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foreseers.chat.adapter.ViewAdapter;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.UserCanumsNumBean;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.global.BaseMainFragment;
import com.foreseers.chat.util.GetLoginTokenUtil;
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
 * 商店
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends BaseMainFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.text_num)
    TextView textNum;
    Unbinder unbinder;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.img5)
    ImageView img5;
    private LoginBean loginBean;
    private UserCanumsNumBean userCanumsNumBean;
    private List<View> viewList = new ArrayList<View>();
    private PagerAdapter viewAdapter;
    private LayoutInflater inflater;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initViews() {
        inflater = LayoutInflater.from(getActivity());
        View view1 = inflater.inflate(R.layout.view_page1, null);
        View view2 = inflater.inflate(R.layout.view_page2, null);
        View view3 = inflater.inflate(R.layout.view_page3, null);
        View view4 = inflater.inflate(R.layout.view_page4, null);
        View view5 = inflater.inflate(R.layout.view_page5, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);

        viewAdapter = new ViewAdapter(viewList);
        viewpager.setAdapter(viewAdapter);
        viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void initDatas() {
        OkGo.<String>post(Urls.Url_UserCanumsNum).tag(this)
                .params("userid", GetLoginTokenUtil.getUserId(getActivity()))
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            userCanumsNumBean = gson.fromJson(response.body(),
                                    UserCanumsNumBean.class);
                            int num = userCanumsNumBean.getData().getNums();
                            textNum.setText(num + "");
                        }
                    }
                });
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //滚动时调用
    }

    @Override
    public void onPageSelected(int i) {
        //当页卡被选中时调用

        Log.i("@@@@@@@", "onPageSelected: " + i);
        switch (i) {
            case 0:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;

            case 1:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;
            case 2:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;
            case 3:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_03);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_02);
                break;
            case 4:
                img1.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img2.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img3.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img4.setBackgroundResource(R.mipmap.icon_site_vip_02);
                img5.setBackgroundResource(R.mipmap.icon_site_vip_03);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //当滚动状态改变时被调用

    }
}
