package com.foreseers.chat.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foreseers.chat.adapter.PeopleAdapter;
import com.foreseers.chat.bean.LocationBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.RecommendBean;
import com.foreseers.chat.bean.UserCanumsNumBean;
import com.foreseers.chat.decoration.GridSectionAverageGapItemDecoration;
import com.foreseers.chat.R;
import com.foreseers.chat.global.BaseMainFragment;
import com.foreseers.chat.util.GetLocation;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.DoubleSlideSeekBar;
import com.google.gson.Gson;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.foreseers.chat.util.JudgeNetAndGPS.isGpsEnabled;


/**
 * 主页
 * 匹配
 */
public class MatchFragment extends BaseMainFragment {

    private final String TAG = "MatchFragment####";

    Unbinder unbinder;

    @BindView(R.id.recycler_people)
    RecyclerView recyclerPeople;
    @BindView(R.id.img_match_filter)
    ImageView imgMatchFilter;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.text_canums_num)
    TextView textCanumsNum;
    private final int DATASUCCESS = 1;
    private final int DATASUCCESS2 = 2;
    private final int DATAFELLED = 0;

    private List<RecommendBean.DataBean> recommendBeans = new ArrayList<>();
    private RecommendBean recommendBean;
    private PeopleAdapter peopleAdapter;
    private String facebookid;
    private String huanXinId;
    private SharedPreferences sPreferences;
    private int mNextRequestPage = 1;
    private LocationBean locationBean;
    private String sex;
    private SharedPreferences userInfo;
    private String ageLittle;
    private String agebig;
    private int distance;
    private RadioGroup radioGroup;
    private View view;
    private DoubleSlideSeekBar doubleslideAge,doubleslideDistance;
    private LoginBean loginBean;
    private UserCanumsNumBean userCanumsNumBean;
    private int num;


    public MatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initViews() {
        textCanumsNum.setText("0");
        initauthority();
        //匹配
        peopleAdapter = new PeopleAdapter(getActivity(), recommendBeans);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerPeople.setLayoutManager(staggeredGridLayoutManager);
        recyclerPeople.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 10, 10));
        recyclerPeople.setAdapter(peopleAdapter);

        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        textCanumsNum.setText("0");
    }

    @Override
    public void initDatas() {
        facebookid = GetLoginTokenUtil.getFaceBookId(getActivity());
        huanXinId = GetLoginTokenUtil.getUserId(getActivity());
        userInfo = getActivity().getSharedPreferences("condition", MODE_PRIVATE);

        getDataForHttp();

        initRefreshLayout();
        swipeLayout.setRefreshing(true);
        refresh();
    }


    private void getDataForHttp() {

        sex = "";
        ageLittle = "12";
        agebig = "50";
        distance = 100;

    }


    private void getDataFromHttp() {


        if (isGpsEnabled(getContext())) {
            GetLocation location = new GetLocation();

            while(locationBean == null){
                locationBean = location.getLocation(getActivity());
            }
            if (locationBean != null) {
                OkGo.<String>post(Urls.Url_UserLocation).tag(this)
                        .params("facebookid", facebookid)
                        .params("country", locationBean.getCountry())
                        .params("city", locationBean.getCity())
                        .params("area", locationBean.getArea())
                        .params("addr", locationBean.getAddr())
                        .params("addrs", locationBean.getAddrs())
                        .params("lat", locationBean.getLat())
                        .params("lng", locationBean.getLng())
                        .params("sex", sex)
                        .params("ageLittle", ageLittle)
                        .params("agebig", agebig)
                        .params("distance", distance)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();
                                loginBean = gson.fromJson(response.body(), LoginBean.class);

                                if (loginBean.getStatus().equals("success")) {
                                    recommendBean = gson.fromJson(response.body(), RecommendBean.class);
                                    recommendBeans = recommendBean.getData();

                                    getHandler().obtainMessage(DATASUCCESS).sendToTarget();

                                } else if (loginBean.getStatus().equals("fail")) {
                                    Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }

        } else {

            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("请先打开GPS");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // 转到手机设置界面，用户设置GPS
                    if (swipeLayout != null) {
                        swipeLayout.setRefreshing(false);
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Toast.makeText(getActivity(), "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
                        MatchFragment.this.startActivityForResult(intent, 33); // 设置完成后返回到原来的界面
                    }
                }
            });
            dialog.setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }


        OkGo.<String>post(Urls.Url_UserCanumsNum).tag(this)
                .params("userid", huanXinId)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            userCanumsNumBean = gson.fromJson(response.body(),
                                    UserCanumsNumBean.class);
                            num = userCanumsNumBean.getData().getNums();
                            getHandler().obtainMessage(DATASUCCESS2).sendToTarget();

                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 33:
                swipeLayout.setRefreshing(true);
                refresh();
                break;
        }
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case DATASUCCESS:
                if (swipeLayout != null) {
                    swipeLayout.setRefreshing(false);
                    if (recommendBeans != null && recommendBeans.size() > 0) {
                        peopleAdapter.setNewData(recommendBeans);

                    }
                }


                break;
            case DATASUCCESS2:
                if (textCanumsNum != null) {
                    textCanumsNum.setText(num + "");
                }
                break;
            case DATAFELLED:
                swipeLayout.setRefreshing(false);
                break;
        }
    }

    @OnClick(R.id.img_match_filter)
    public void onViewClicked() {

        initPopupWind();


        radioGroup.setOnCheckedChangeListener(new RadioGroup
                .OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_left://男M
                        sex = "M";
                        Log.i("radioGroup", "onCheckedChanged: " + sex);
                        break;
                    case R.id.radio_middle://不限
                        sex = "";
                        Log.i("radioGroup", "onCheckedChanged: " + sex);
                        break;
                    case R.id.radio_right://女F
                        sex = "F";
                        Log.i("radioGroup", "onCheckedChanged: " + sex);
                        break;


                }
                Log.i("radioGroup", "onCheckedChanged: " + sex);
            }
        });

        doubleslideAge.setOnRangeListener(new DoubleSlideSeekBar.onRangeListener() {
            @Override
            public void onRange(float low, float big) {
                ageLittle = doubleslideAge.getSmallRange() + "";
                agebig = doubleslideAge.getBigRange() + "";
            }
        });
        doubleslideDistance.setOnRangeListener(new DoubleSlideSeekBar.onRangeListener() {
            @Override
            public void onRange(float low, float big) {
                distance = (int) doubleslideDistance.getBigRange();
            }
        });



        new PopWindow.Builder(getActivity())
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .addContentView(view)
                .addItemAction(new PopItemAction("确定", PopItemAction.PopItemStyle.Cancel, new
                        PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {
//                                Toast.makeText(getActivity(), "确定", Toast.LENGTH_LONG).show();


//                                SharedPreferences.Editor editor = userInfo.edit();//获取Editor
//                                // 得到Editor后，写入需要保存的数据
//                                editor.putString("sex", sex);
//                                editor.putString("ageLittle", ageLittle);
//                                editor.putString("agebig", agebig);
//                                editor.putInt("distance", distance);
//                                editor.commit();//提交修改
//                                Log.e("okgo", "sex: " + sex + "  ageLittle:" + ageLittle + "   " +
//                                        "agebig:" + agebig + "    distance:" + distance);
//                                Log.e("okgo", "sex:" + sex);
//


                                Log.i(TAG, "onClickPopWindow: "+ageLittle);
                                getDataFromHttp();
                                getDataForHttp();
                            }
                        }))
                .show();

    }

    private void initPopupWind() {
        view = View.inflate(getActivity(), R.layout.item_match_filter, null);
        radioGroup = view.findViewById(R.id.radioGroup);

        RadioButton radioButton_left = view.findViewById(R.id.radio_left);
        RadioButton radioButton_middle = view.findViewById(R.id.radio_middle);
        RadioButton radioButton_right = view.findViewById(R.id.radio_right);


//        sex = userInfo.getString("sex", "");
        switch (sex) {
            case "M"://男M
                radioButton_left.setChecked(true);
                Log.i("radioGroup", "onCheckedChanged: " + sex);
                break;
            case ""://不限
                radioButton_middle.setChecked(true);
                Log.i("radioGroup", "onCheckedChanged: " + sex);
                break;
            case "F"://女F
                radioButton_right.setChecked(true);
                Log.i("radioGroup", "onCheckedChanged: " + sex);
                break;

        }


        doubleslideAge = view.findViewById(R.id.doubleslide_age);
        doubleslideDistance=view.findViewById(R.id.doubleslide_distance);
//        distance = userInfo.getInt("distance", 0);

    }

    private void initRefreshLayout() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    /**
     * 位置权限
     */
    private void initauthority() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);
        }
    }

    private void refresh() {
        mNextRequestPage = 1;
        peopleAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        getDataFromHttp();
    }

}
