package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.FortunetellingOutlineBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.global.MyApplication;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 命书概要
 */
public class FortunetellingOutlineActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_ok) TextView textOk;
    @BindView(R.id.text_constellation) TextView textConstellation;
    @BindView(R.id.text_harmony_zodiac) TextView textHarmonyZodiac;
    @BindView(R.id.img_1) ImageView img1;
    @BindView(R.id.text_zodiac) TextView textZodiac;
    @BindView(R.id.text_zodiacmatch) TextView textZodiacmatch;
    @BindView(R.id.img_2) ImageView img2;
    @BindView(R.id.text_star) TextView textStar;
    @BindView(R.id.text_starmatch) TextView textStarmatch;
    @BindView(R.id.img_3) ImageView img3;
    @BindView(R.id.text_numerology) TextView textNumerology;
    @BindView(R.id.text_numerologymatch) TextView textNumerologymatch;
    @BindView(R.id.img_4) ImageView img4;
    @BindView(R.id.text_ziwei) TextView textZiwei;
    @BindView(R.id.text_ziweimatch) TextView textZiweimatch;
    @BindView(R.id.img_5) ImageView img5;
    @BindView(R.id.text_bazi) TextView textBazi;
    @BindView(R.id.text_bazimatch) TextView textBazimatch;
    @BindView(R.id.img_6) ImageView img6;

    private FortunetellingOutlineBean.DataBean dataBean;
    private FortunetellingOutlineBean fortunetellingOutlineBean;
    private String lifeuserid;
    private NSDictionary dic;

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
        setContentView(R.layout.activity_fortunetelling_outline);
        ButterKnife.bind(this);
        try {
            dic = (NSDictionary) PropertyListParser.parse(MyApplication.getContext()
                                                                  .getAssets()
                                                                  .open("LifeBook.plist"));
            //            String url = (dic.objectForKey("color_andriod")).toJavaObject().toString();
            //            Log.i("FortunetellingOutline", "initViews: "+url);
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
    }

    @Override
    public void initDatas() {
        textName.setText(getIntent().getStringExtra("name"));
        lifeuserid = getIntent().getStringExtra("lifeuserid");
        OkGo.<String>post(Urls.Url_LifebookUser).tag(this)
                .params("lifeuserid", lifeuserid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            fortunetellingOutlineBean = gson.fromJson(response.body(), FortunetellingOutlineBean.class);
                            dataBean = fortunetellingOutlineBean.getData();

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
                textConstellation.setText(getResources().getString(R.string.txt_horoscope) + ":\t" + dataBean.getHoroscope());
                textHarmonyZodiac.setText(getResources().getString(R.string.text_harmony_zodiac) + ":\t" + dataBean.getHoroscopematch());
                img1.setBackgroundResource(getResources().getIdentifier((dic.objectForKey(dataBean.getHoroscope())).toJavaObject().toString(), "mipmap", getPackageName()));

                textZodiac.setText(getResources().getString(R.string.txt_zodiac) + ":\t" +dataBean.getZodiac());
                textZodiacmatch.setText(getResources().getString(R.string.txt_zodiac) + ":\t" +dataBean.getZodiacmatch());
                img2.setBackgroundResource(getResources().getIdentifier((dic.objectForKey(dataBean.getZodiac())).toJavaObject().toString(), "mipmap", getPackageName()));

                textStar.setText(getResources().getString(R.string.txt_star) + ":\t" +dataBean.getStar());
                textStarmatch.setText(getResources().getString(R.string.txt_star) + ":\t" +dataBean.getStarmatch());
                img3.setBackgroundResource(getResources().getIdentifier((dic.objectForKey(dataBean.getStar()+"")).toJavaObject().toString(),
                                                                        "mipmap", getPackageName()));

                textNumerology.setText(getResources().getString(R.string.txt_numerology) + ":\t" +dataBean.getNumerology());
                textNumerologymatch.setText(getResources().getString(R.string.txt_numerology) + ":\t" +dataBean.getNumerologymatch());
                img4.setBackgroundResource(getResources().getIdentifier((dic.objectForKey(dataBean.getNumerology()+"")).toJavaObject().toString(),
                                                                        "mipmap", getPackageName()));

                textZiwei.setText(getResources().getString(R.string.txt_ziwei) + ":\t" +dataBean.getZiwei());
                textZiweimatch.setText(getResources().getString(R.string.txt_ziwei) + ":\t" +dataBean.getZiweimatch());
                img5.setBackgroundResource(getResources().getIdentifier((dic.objectForKey(dataBean.getZiwei())).toJavaObject().toString(),
                                                                        "mipmap", getPackageName()));

                String bazi =dataBean.getBazi().split(",")[2];
                String i= String.valueOf(bazi.charAt(1));

                Log.d("tellingOutlineActivity", "processHandlerMessage: "+bazi+"     i: "+i);
                textBazi.setText(getResources().getString(R.string.txt_bazi) + ":\t" +i);
                textBazimatch.setText(getResources().getString(R.string.txt_bazi) + ":\t" +dataBean.getBazimatch());
                img6.setBackgroundResource(getResources().getIdentifier((dic.objectForKey(i)).toJavaObject().toString(),
                                                                        "mipmap", getPackageName()));


                break;
        }
    }

    @OnClick(R.id.text_ok)
    public void onViewClicked() {
        Intent intent = new Intent(this, FortunetellingActivity.class);
        intent.putExtra("lifeuserid", lifeuserid);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        startActivity(intent);
        finish();
    }
}
