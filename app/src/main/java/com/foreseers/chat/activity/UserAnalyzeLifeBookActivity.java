package com.foreseers.chat.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.foreseers.chat.R;
import com.foreseers.chat.bean.AnalyzeLifeBookBean;
import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.util.GetLoginTokenUtil;
import com.foreseers.chat.util.GlideUtil;
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
import butterknife.OnClick;

/**
 * 我與TA的詳細分析
 */
public class UserAnalyzeLifeBookActivity extends BaseActivity {


    @BindView(R.id.item_popular)
    RImageView itemPopular;
    @BindView(R.id.characterScore)
    DecoView characterScore;

    @BindView(R.id.mindScore)
    DecoView mindScore;
    @BindView(R.id.mindScoreTxt)
    TextView mindScoreTxt;
    @BindView(R.id.bodyScore)
    DecoView bodyScore;
    @BindView(R.id.bodyScoreTxt)
    TextView bodyScoreTxt;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.progress_matching_rate)
    ProgressBar progressMatchingRate;
    @BindView(R.id.progress_text)
    TextView progressText;
    @BindView(R.id.container_score)
    LinearLayout containerScore;
    @BindView(R.id.text_commentdesc)
    TextView textCommentdesc;
    @BindView(R.id.text_characteristicdesc)
    TextView textCharacteristicdesc;
    @BindView(R.id.text_courtingdesc)
    TextView textCourtingdesc;

    private final int DATASUCCESS = 1;
    private final int DATAFELLED = 2;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.text_sign)
    TextView textSign;
    @BindView(R.id.characterScoreTxt)
    TextView characterScoreTxt;
    @BindView(R.id.text_distance)
    TextView textDistance;
    @BindView(R.id.text_desc)
    TextView textDesc;
    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;

    private String facebookid;
    private String userid;
    private SharedPreferences sPreferences;
    private AnalyzeLifeBookBean analyzeLifeBookBean;
    private AnalyzeLifeBookBean.DataBean dataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_life_book);
        ButterKnife.bind(this);
        getData();
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {

        userid = getIntent().getStringExtra("userid");

        getDataFormHttp();

    }

    private void getDataFormHttp() {
        OkGo.<String>post(Urls.Url_AnalyzeLifeBook).tag(this)
                .params("uid", GetLoginTokenUtil.getUserId(this))
                .params("userid", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response.body(), LoginBean.class);
                        if (loginBean.getStatus().equals("success")) {
                            analyzeLifeBookBean = gson.fromJson(response.body(), AnalyzeLifeBookBean.class);
                            dataBean = analyzeLifeBookBean.getData();
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
                    GlideUtil.glide(UserAnalyzeLifeBookActivity.this, dataBean.getHead(), itemPopular);
                    textName.setText(dataBean.getName());

                    switch (dataBean.getSex()) {

                        case "F":
                            textSex.setText("♀ " + dataBean.getAge());
                            break;
                        case "M":
                            textSex.setText("♂ " + dataBean.getAge());
                            break;
                    }

                    textSign.setText(dataBean.getObligate());
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
                    progressText.setText("匹配度" + dataBean.getUserscore() + "%");
                    textCommentdesc.setText(dataBean.getCommentdesc());
                    textCharacteristicdesc.setText(dataBean.getCharacteristicdesc());
                    textCourtingdesc.setText(dataBean.getSpare());
                    break;
                case DATAFELLED:
                    Toast.makeText(UserAnalyzeLifeBookActivity.this, "网络连接失败", Toast
                            .LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initView(int characterscore, int mindscore, int bodyscore) {
        int series1Index = setDecoView(characterScore, Color.rgb(233, 233, 235), Color.rgb(100,
                168, 235), characterscore, 20f);
        int series2Index = setDecoView(mindScore, Color.rgb(233, 233, 235), Color.rgb(248,
                156, 166), mindscore, 20f);
        int series3Index = setDecoView(bodyScore, Color.rgb(233, 233, 235), Color.rgb(182,
                217, 159), bodyscore, 20f);

    }

    public int setDecoView(DecoView v, int c1, int c2, int s, float lw) {

        v.addSeries(new SeriesItem.Builder(c1)
                .setRange(0, 100, 100).setInitialVisibility(true).setLineWidth(lw).build());

        SeriesItem seriesItem1 = new SeriesItem.Builder(c2)
                .setRange(0, 100, s).setLineWidth(lw).build();

        return v.addSeries(seriesItem1);

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

    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }
}
