package com.foreseers.chat.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foreseers.chat.R;
import com.foreseers.chat.bean.AnalyzeLifeBookBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.GlideUtil;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.decoviewlib.DecoView;
import com.foreseers.chat.view.decoviewlib.charts.SeriesItem;
import com.foreseers.chat.view.widget.MyTitleBar;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruffian.library.widget.RImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我與TA的詳細分析
 */
public class UserAnalyzeLifeBookActivity extends BaseActivity {

    @BindView(R.id.item_popular) RImageView itemPopular;
    @BindView(R.id.characterScore) DecoView characterScore;

    @BindView(R.id.mindScore) DecoView mindScore;
    @BindView(R.id.mindScoreTxt) TextView mindScoreTxt;
    @BindView(R.id.bodyScore) DecoView bodyScore;
    @BindView(R.id.bodyScoreTxt) TextView bodyScoreTxt;
    @BindView(R.id.score) TextView score;
    @BindView(R.id.progress_matching_rate) ProgressBar progressMatchingRate;
    @BindView(R.id.progress_text) TextView progressText;
    @BindView(R.id.container_score) LinearLayout containerScore;
    @BindView(R.id.text_commentdesc) TextView textCommentdesc;
    @BindView(R.id.text_characteristicdesc) TextView textCharacteristicdesc;
    @BindView(R.id.text_courtingdesc) TextView textCourtingdesc;

    @BindView(R.id.text_name) TextView textName;
    @BindView(R.id.text_sex) TextView textSex;
    @BindView(R.id.text_sign) TextView textSign;
    @BindView(R.id.characterScoreTxt) TextView characterScoreTxt;
    @BindView(R.id.text_distance) TextView textDistance;
    @BindView(R.id.text_desc) TextView textDesc;
    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.text_characteristicgood) TextView textCharacteristicgood;
    @BindView(R.id.text_spare1) TextView textSpare1;
    @BindView(R.id.text_1) TextView text1;
    @BindView(R.id.text_2) TextView text2;
    @BindView(R.id.text_3) TextView text3;

    private String facebookid;
    private String userid;
    private SharedPreferences sPreferences;
    private AnalyzeLifeBookBean analyzeLifeBookBean;
    private AnalyzeLifeBookBean.DataBean dataBean;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getDataFormHttp() {
        OkGo.<String>post(Urls.Url_AnalyzeLifeBook).tag(this)
                .params("uid", PreferenceManager.getUserId(this))
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus()
                                .equals("success")) {
                            analyzeLifeBookBean = gson.fromJson(response.body(), AnalyzeLifeBookBean.class);
                            dataBean = analyzeLifeBookBean.getData();
                            getHandler().obtainMessage(DATASUCCESS)
                                    .sendToTarget();
                        } else if (loginBean.getStatus()
                                .equals("fail")) {
                            getHandler().obtainMessage(DATAFELLED)
                                    .sendToTarget();
                        }
                    }
                });
    }

    private void initView(int characterscore, int mindscore, int bodyscore) {
        int series1Index = setDecoView(characterScore, Color.rgb(233, 233, 235), Color.rgb(100, 168, 235), characterscore, 20f);
        int series2Index = setDecoView(mindScore, Color.rgb(233, 233, 235), Color.rgb(248, 156, 166), mindscore, 20f);
        int series3Index = setDecoView(bodyScore, Color.rgb(233, 233, 235), Color.rgb(182, 217, 159), bodyscore, 20f);

        typeface = Typeface.createFromAsset(this.getAssets(), "华康华综体W5-B5.TTF");
        text1.setTypeface(typeface);
        text2.setTypeface(typeface);
        text3.setTypeface(typeface);
        characterScoreTxt.setTypeface(typeface);
        mindScoreTxt.setTypeface(typeface);
        bodyScoreTxt.setTypeface(typeface);
    }

    public int setDecoView(DecoView v, int c1, int c2, int s, float lw) {

        v.addSeries(new SeriesItem.Builder(c1).setRange(0, 100, 100)
                            .setInitialVisibility(true)
                            .setLineWidth(lw)
                            .build());

        SeriesItem seriesItem1 = new SeriesItem.Builder(c2).setRange(0, 100, s)
                .setLineWidth(lw)
                .build();

        return v.addSeries(seriesItem1);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_user_analyze_life_book);
        ButterKnife.bind(this);
    }

    @Override
    public void initDatas() {
        userid = getIntent().getStringExtra("userid");
        getDataFormHttp();
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
                GlideUtil.glideMatch(UserAnalyzeLifeBookActivity.this, dataBean.getHead(), itemPopular);
                textName.setText(dataBean.getName());

                switch (dataBean.getSex()) {

                    case "F":
                        textSex.setText("♀ " + dataBean.getAge());
                        textSex.setBackgroundResource(R.drawable.rounded_layout_pink);
                        break;
                    case "M":
                        textSex.setText("♂ " + dataBean.getAge());
                        textSex.setBackgroundResource(R.drawable.rounded_layout_blue);
                        break;
                }

                if (dataBean.getObligate() != null) {
                    textSign.setText(dataBean.getObligate());
                }

                textDistance.setText(dataBean.getDistance() + " km +");
                textDesc.setText(dataBean.getUserdesc());
                int characterscore = dataBean.getCharacterscore();
                int mindscore = dataBean.getMindscore();
                int bodyscore = dataBean.getBodyscore();
                initView(characterscore, mindscore, bodyscore);
                characterScoreTxt.setText(characterscore + "");

                mindScoreTxt.setText(mindscore + "");
                bodyScoreTxt.setText(bodyscore + "");

                Log.i("11111111", characterscore + "   " + mindscore + "    " + bodyscore);

                progressMatchingRate.setProgress(dataBean.getUserscore());
                progressText.setText(this.getResources()
                                             .getString(R.string.progress) + dataBean.getUserscore() + "%");
                textCommentdesc.setText(dataBean.getCommentdesc());
                textCharacteristicgood.setText(dataBean.getCharacteristicgood());
                textCharacteristicdesc.setText(dataBean.getCharacteristicdesc());
                textCourtingdesc.setText(dataBean.getSpare());
                textSpare1.setText(dataBean.getSpare1());
                break;
            case DATAFELLED:
                Toast.makeText(UserAnalyzeLifeBookActivity.this, getActivity().getResources()
                        .getString(R.string.text_err), Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }
}
