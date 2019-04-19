package com.foreseers.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.FortunetellingContentBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FortunetellingContentActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.text_content) TextView textContent;
    @BindView(R.id.img) ImageView img;
    private Bundle bundle;
    private FortunetellingContentBean dataBean;
    private List<FortunetellingContentBean.DataBean> dataBeanList = new ArrayList<>();
    private NSDictionary dic;
    private KProgressHUD hud;

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
        setContentView(R.layout.activity_fortunetelling_content);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        myTitlebar.setTitle(bundle.getString("title"));
        try {
            dic = (NSDictionary) PropertyListParser.parse(MyApplication.getContext()
                                                                  .getAssets()
                                                                  .open("LifeBook.plist"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        //                .setLabel("Please wait")
        //                .setDetailsLabel("Downloading data")
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                //                .setLabel("Please wait")
                //                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    public void initDatas() {

        OkGo.<String>post(Urls.Url_LifeBookDetailname).tag(this)
                .params("lifeuserid", bundle.getString("lifeuserid"))
                .params("name", bundle.getString("name"))
                .params("title", bundle.getString("title"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            dataBean = gson.fromJson(response.body(), FortunetellingContentBean.class);
                            dataBeanList = dataBean.getData();
                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
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

        switch (msg.what) {
            case DATASUCCESS:

                img.setBackgroundResource(getResources().getIdentifier((dic.objectForKey(dataBeanList.get(0).getIcon())).toJavaObject().toString(), "mipmap", getPackageName()));
                textContent.setText(Html.fromHtml(dataBeanList.get(0).getComment()));
                hud.dismiss();
                break;
        }
    }
}
