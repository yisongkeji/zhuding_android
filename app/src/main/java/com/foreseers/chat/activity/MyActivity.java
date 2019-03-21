package com.foreseers.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.bean.MyBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyActivity extends BaseActivity {

    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.img_ani)
    ImageView imgAni;
    @BindView(R.id.layout_wipe)
    LinearLayout layoutWipe;
    @BindView(R.id.text_user_details_name)
    TextView textUserDetailsName;
    @BindView(R.id.img_vip)
    ImageView imgVip;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_age)
    TextView textAge;
    @BindView(R.id.text_ziwei)
    TextView textZiwei;
    @BindView(R.id.text_sign)
    TextView textSign;
    @BindView(R.id.mylayout)
    LinearLayout mylayout;
    private final int DATASUCCESS = 1;
    private final int DATASUCCESS2 = 2;
    private List<String> imgList = new ArrayList<>();
    private MyBean myBean;
    private MyBean.DataBean dataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置图片加载器
        banner.setImageLoader(new GlideUtil.GlideImageLoader());
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {
        getDataFromHttp();
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {
        switch (msg.what) {
            case DATASUCCESS:
                if (banner != null) {
                    //设置图片集合
                    banner.update(imgList);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();

                    switch (dataBean.getVip()) {
                        case 0:
                            imgVip.setVisibility(View.GONE);
                            break;
                        case 1:
                            imgVip.setVisibility(View.VISIBLE);
                            break;
                    }

                    textUserDetailsName.setText(dataBean.getName());
                    textNum.setText(dataBean.getNum() + "");
                    textAge.setText(dataBean.getAge() + "");
                    switch (dataBean.getSex()) {
                        case "F":
                            textSex.setText("女");
                            break;
                        case "M":
                            textSex.setText("男");
                            break;
                    }
                    textZiwei.setText(dataBean.getZiwei());
                    textSign.setText(dataBean.getSign());
                }

                break;

            case DATASUCCESS2:

                break;

        }
    }

    private void getDataFromHttp() {
        OkGo.<String>post(Urls.Url_UserShow).tag(this)
                .params("userid", PreferenceManager.getUserId(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            myBean = gson.fromJson(response.body(), MyBean.class);
                            dataBean = myBean.getData();
                            imgList.add(dataBean.getHead());
                            for (int i = 0; i < dataBean.getImages().size(); i++) {
                                imgList.add( dataBean.getImages().get(i));
                            }



                            getHandler().obtainMessage(DATASUCCESS).sendToTarget();
                        }
                    }
                });
    }
}
