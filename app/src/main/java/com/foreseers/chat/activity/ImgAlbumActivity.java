package com.foreseers.chat.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.foreseers.chat.bean.AlbumBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ImgAlbumActivity extends AppCompatActivity {

    private final String TAG = "ImgAlbumActivity@@@@@@@";
    @BindView(R.id.banner)
    Banner banner;


    private String userid;
    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    private LoginBean loginBean;
    private AlbumBean albumBean;
    private List<String> imgList = new ArrayList<>();

    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_album);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        position = bundle.getInt("position");
        Log.i(TAG, "position: "+position);
        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        getDataFromHttp();


    }

    private void getDataFromHttp() {

        userid =GetLoginTokenUtil.getUserId(this);
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


                    //设置图片集合
                    banner.update(imgList);


                    //banner设置方法全部调用完毕时最后调用
                    banner.start();

                    break;
                case DATAFELLED:

                    break;
            }
        }
    };

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {


            //Glide 加载图片简单用法
            Glide.with(context).load(path).error(R.mipmap.default_image).into(imageView);

        }

    }

}
