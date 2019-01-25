package chat.foreseers.com.foreseers.fragment;


import android.Manifest;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.adapter.PeopleAdapter;
import chat.foreseers.com.foreseers.bean.LocationBean;
import chat.foreseers.com.foreseers.bean.LoginBean;
import chat.foreseers.com.foreseers.bean.RecommendBean;
import chat.foreseers.com.foreseers.global.BaseMainFragment;
import chat.foreseers.com.foreseers.util.GetLocation;
import chat.foreseers.com.foreseers.util.Urls;
import chat.foreseers.com.foreseers.view.DoubleSlideSeekBar;

import static android.content.Context.MODE_PRIVATE;


/**
 * 匹配
 */
public class MatchFragment extends BaseMainFragment {


    Unbinder unbinder;

    @BindView(R.id.recycler_people)
    RecyclerView recyclerPeople;
    @BindView(R.id.img_match_filter)
    ImageView imgMatchFilter;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private final int DATASUCCESS = 1;
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
        initauthority();
        //匹配
        peopleAdapter = new PeopleAdapter(getActivity(), recommendBeans);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerPeople.setLayoutManager(gridLayoutManager);
        recyclerPeople.setAdapter(peopleAdapter);

        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));


    }

    @Override
    public void initDatas() {

        if (facebookid == null || huanXinId == null) {
            sPreferences = getActivity().getSharedPreferences("loginToken", MODE_PRIVATE);
            facebookid = sPreferences.getString("token", "");
            huanXinId = sPreferences.getString("huanXinId", "");
            userInfo = getActivity().getSharedPreferences("condition", MODE_PRIVATE);
            Log.i("condition", "initDatas: sex" + userInfo.getString("sex", ""));
            Log.i("condition", "initDatas: ageLittle" + userInfo.getString("ageLittle", ""));
            Log.i("condition", "initDatas: agebig" + userInfo.getString("agebig", ""));
            Log.i("condition", "initDatas: distance" + userInfo.getInt("distance", 0));


            sex = userInfo.getString("sex", "");
            if (sex == null) {
                sex = "";
            }
            ageLittle = userInfo.getString("ageLittle", "");
            if (ageLittle == null||ageLittle.equals("")) {
                ageLittle = "12";
            }
            agebig = userInfo.getString("agebig", "");
            if (agebig.equals("")||agebig==null) {
                agebig = "50";
            }
            Log.i("condition", "initData########s: agebig" + agebig+"ageLittle"+ageLittle);
            distance = userInfo.getInt("distance", 0);
            if (distance == 0) {
                distance = 100;
            }
        }


        initRefreshLayout();
        swipeLayout.setRefreshing(true);
        refresh();
    }


    private void getDataFromHttp() {
        GetLocation location = new GetLocation();
        locationBean = location.getLocation(getActivity());

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
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);

                        if (loginBean.getStatus().equals("success")) {
                            recommendBean = gson.fromJson(response.body(), RecommendBean.class);
                            recommendBeans = recommendBean.getData();

                            getHandler().obtainMessage(DATASUCCESS).sendToTarget();

                        } else if (loginBean.getStatus().equals("fail")) {
                            Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case DATASUCCESS:
                if (recommendBeans != null && recommendBeans.size() > 0) {
                    peopleAdapter.setNewData(recommendBeans);
                    swipeLayout.setRefreshing(false);
                }
                break;

            case DATAFELLED:
                swipeLayout.setRefreshing(false);
                break;
        }
    }

    @OnClick(R.id.img_match_filter)
    public void onViewClicked() {
        final View view = View.inflate(getActivity(), R.layout.item_match_filter, null);


        new PopWindow.Builder(getActivity())
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .addContentView(view)
                .addItemAction(new PopItemAction("确定", PopItemAction.PopItemStyle.Cancel, new
                        PopItemAction.OnClickListener() {
                            @Override
                            public void onClick() {
                                Toast.makeText(getActivity(), "确定", Toast.LENGTH_LONG).show();

                                RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
                                radioGroup.setOnCheckedChangeListener(new RadioGroup
                                        .OnCheckedChangeListener() {

                                    @Override
                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                        switch (i) {
                                            case R.id.radio_left://男M
                                                sex = "M";
                                                break;
                                            case R.id.radio_middle://不限
                                                sex = "";
                                                break;
                                            case R.id.radio_right://女F
                                                sex = "F";
                                                break;


                                        }
                                    }
                                });

                                DoubleSlideSeekBar doubleslide_age = view.findViewById(R.id
                                        .doubleslide_age);

                                ageLittle = doubleslide_age.getSmallRange() + "";
                                agebig = doubleslide_age.getBigRange() + "";
                                Log.i("@@@@@@@@@@", "onClick: " + ageLittle);
                                DoubleSlideSeekBar doubleslide_distance = view.findViewById(R.id
                                        .doubleslide_distance);

                                distance = (int) doubleslide_distance.getBigRange();


                                SharedPreferences.Editor editor = userInfo.edit();//获取Editor
                                // 得到Editor后，写入需要保存的数据
                                editor.putString("sex", sex);
                                editor.putString("ageLittle", ageLittle);
                                editor.putString("agebig", agebig);
                                editor.putInt("distance", distance);
                                editor.commit();//提交修改
                                Log.i("huanXinId", "isLogin: " + userInfo.getString("huanXinId",
                                        ""));
                            }
                        }))
                .show();

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
