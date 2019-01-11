package chat.foreseers.com.foreseers.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruffian.library.widget.RImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.view.decoviewlib.DecoView;
import chat.foreseers.com.foreseers.view.decoviewlib.charts.SeriesItem;

/**
 * 我與TA的詳細分析
 */
public class UserAnalyzeLifeBookActivity extends AppCompatActivity {

    @BindView(R.id.goback)
    FrameLayout goback;
    @BindView(R.id.item_popular)
    RImageView itemPopular;
    @BindView(R.id.characterScore)
    DecoView characterScore;
    @BindView(R.id.characterScoreTxt)
    TextView characterScoreTxt;
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
    private int mindScore1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_life_book);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        mindScore1 = 40;
    }

    private void initView() {
        int series1Index = setDecoView(characterScore, Color.rgb(233, 233, 235), Color.rgb(100,
                168, 235), mindScore1, 20f);
        int series2Index = setDecoView(mindScore, Color.rgb(233, 233, 235), Color.rgb(248,
                156, 166), mindScore1, 20f);
        int series3Index = setDecoView(bodyScore, Color.rgb(233, 233, 235), Color.rgb(182,
                217, 159), mindScore1, 20f);

    }

    public int setDecoView(DecoView v, int c1, int c2, int s, float lw) {

        v.addSeries(new SeriesItem.Builder(c1)
                .setRange(0, 100, 100).setInitialVisibility(true).setLineWidth(lw).build());

        SeriesItem seriesItem1 = new SeriesItem.Builder(c2)
                .setRange(0, 100, s).setLineWidth(lw).build();

        return v.addSeries(seriesItem1);

    }

    @OnClick(R.id.goback)
    public void onViewClicked() {
        finish();
    }
}
