package com.foreseers.chat.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.adapter.ImgPagerAdapter;
import com.foreseers.chat.bean.AlbumBean;
import com.foreseers.chat.bean.CustomViewsInfo;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImgAlbumActivity extends AppCompatActivity {

    private final String TAG = "ImgAlbumActivity@@@@@@@";
    @BindView(R.id.banner)
    XBanner banner;

    private String userid;
    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private LoginBean loginBean;
    private AlbumBean albumBean;
    private List<String> imgList = new ArrayList<>();
    private ImgPagerAdapter imgPagerAdapter;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_album);
        ButterKnife.bind(this);
        getLoginToken();

        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        position = bundle.getInt("position");
        getDataFromHttp();
        banner.setBannerCurrentItem(position);

//        Log.i(TAG, "onCreate: "+dataList.toString());
        Log.i(TAG, "data onCreate: " + data);
    }

    private void getDataFromHttp() {
        OkGo.<String>post(Urls.Url_My).tag(this)
                .params("userid", userid)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            albumBean = gson.fromJson(response.body(), AlbumBean.class);

                            imgList = albumBean.getData().getListimage();

                            mHandler.obtainMessage(DATASUCCESS).sendToTarget();

                        } else if (loginBean.getStatus().equals("fail")) {
                            mHandler.obtainMessage(DATAFELLED).sendToTarget();

                        }


                    }
                });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DATASUCCESS:
//                    List<CustomViewsInfo> data = new ArrayList<>();
//                    for (int i = 0; i < imgList.size() ; i++) {
//
//                        data.add(new CustomViewsInfo(data.get(i)));
//
//                    }

                    banner.loadImage(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            Glide.with(ImgAlbumActivity.this).load(imgList.get(position)).placeholder(R.mipmap.default_image).error(R.mipmap.default_image).into((ImageView) view);

                        }
                    });
//                    banner.setBannerData(R.layout.item_imgpager, imgList);

                    break;
                case DATAFELLED:

                    break;
            }
        }
    };


    /**
     * 获取登录token（facebookID）
     */

    public void getLoginToken() {
        SharedPreferences userInfo = getSharedPreferences("loginToken", MODE_PRIVATE);

        userid = userInfo.getString("huanXinId", "");
    }
}
