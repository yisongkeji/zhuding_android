package chat.foreseers.com.foreseers.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.activity.UserDetailsActivity;
import chat.foreseers.com.foreseers.bean.PeopleBean;
import chat.foreseers.com.foreseers.bean.RecommendBean;

public class PeopleAdapter extends BaseQuickAdapter<RecommendBean.DataBean, BaseViewHolder> {

    Activity context;
    @BindView(R.id.text_people_progress)
    TextView textPeopleProgress;
    @BindView(R.id.text_people_sex)
    TextView textPeopleSex;
    @BindView(R.id.text_people_age)
    TextView textPeopleAge;
    @BindView(R.id.text_people_location)
    TextView textPeopleLocation;
    @BindView(R.id.text_people_name)
    TextView textPeopleName;
    @BindView(R.id.layout_item_people)
    FrameLayout layoutItemPeople;
    private final Typeface typeface;

    public PeopleAdapter(Activity context, @Nullable List<RecommendBean.DataBean> data) {
        super(R.layout.item_people, data);
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "华康华综体W5-B5.TTF");
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final RecommendBean.DataBean item) {


        switch (item.getNumuser()) {
            case 0:
                baseViewHolder.setBackgroundRes(R.id.item_background, R.drawable
                        .icon_header_gradient_01);
                baseViewHolder.setVisible(R.id.item_recommend_img, false);
                break;

            case 1:
                baseViewHolder.setBackgroundRes(R.id.item_background, R.drawable
                        .icon_header_gradient_02);
                baseViewHolder.setVisible(R.id.item_recommend_img, true);
                break;
            default:
                break;
        }


//        baseViewHolder.setBackgroundRes(R.id.item_background, (item.getNumuser() == 1 ? R.drawable
//                .icon_header_gradient_02 : R.drawable.icon_header_gradient_01));
//
//        baseViewHolder.setVisible(R.id.item_recommend_img, (item.getNumuser() == 1 ? true :
// false));

//            baseViewHolder.setBackgroundRes(R.id.item_background, R.drawable
//                    .icon_header_gradient_01);
//            baseViewHolder.setVisible(R.id.item_recommend_img, false);


        baseViewHolder.setTypeface(typeface);
        baseViewHolder
                .setText(R.id.text_people_name, item.getNumuser() + item.getUsername())
                .setText(R.id.text_people_progress, "匹配度" + item.getUserscore() + "%")
                .setText(R.id.text_people_sex, (item.getSex().equals("F") ? "女" : "男"))
                .setText(R.id.text_people_age, item.getDate() + "歲")
                .setText(R.id.text_people_location, "距离:" + item.getDistance() + "km");

        baseViewHolder.getView(R.id.image_people).findViewById(R.id.image_people)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, UserDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("userid", item.getId());
                        bundle.putString("username", item.getUsername());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });

    }


}
