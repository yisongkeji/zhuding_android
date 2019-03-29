package com.foreseers.chat.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.activity.UserDetailsActivity;
import com.foreseers.chat.bean.RecommendBean;
import com.foreseers.chat.util.GlideUtil;

import java.util.List;

import butterknife.BindView;

public class PeopleAdapter extends BaseQuickAdapter<RecommendBean.DataBean, BaseViewHolder> {

    Activity context;
    @BindView(R.id.text_people_progress)
    TextView textPeopleProgress;
    @BindView(R.id.text_people_sex)
    TextView textPeopleSex;

    @BindView(R.id.text_people_location)
    TextView textPeopleLocation;
    @BindView(R.id.text_people_name)
    TextView textPeopleName;
    @BindView(R.id.layout_item_people)
    FrameLayout layoutItemPeople;
    private final Typeface typeface;
    private ImageView image;

    public PeopleAdapter(Activity context, @Nullable List<RecommendBean.DataBean> data) {
        super(R.layout.item_people, data);
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "华康华综体W5-B5.TTF");
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final RecommendBean.DataBean item) {

        image = baseViewHolder.getView(R.id.image_people).findViewById(R.id.image_people);

        GlideUtil.glideMatch(context, item.getHead(), image);


        switch (item.getNumuser()) {
            case 0:
                baseViewHolder.setVisible(R.id.layout_renqi, false);
                break;
            case 1:
                baseViewHolder.setVisible(R.id.layout_renqi, true);
                break;
        }


        baseViewHolder.setTypeface(typeface);
        baseViewHolder.setText(R.id.text_people_name, item.getUsername())
                .setText(R.id.text_people_ziwei, item.getZiwei())
                .setText(R.id.text_desc, item.getDesc())
                .setText(R.id.text_people_progress, item.getUserscore() + "")
                .setText(R.id.text_people_location, item.getDistance() + "km");


        switch (item.getSex()) {
            case "F":
                baseViewHolder.getView(R.id.text_people_sex).setBackgroundResource(R.drawable
                        .rounded_layout_pink);
                baseViewHolder.setText(R.id.text_people_sex, ("♀" + item.getReservedint()));

                break;
            case "M":
                baseViewHolder.getView(R.id.text_people_sex).setBackgroundResource(R.drawable
                        .rounded_layout_blue);
                baseViewHolder.setText(R.id.text_people_sex, ("♂" + item.getReservedint()));

                break;

            default:
                break;
        }


        baseViewHolder.getView(R.id.item_background).findViewById(R.id.item_background)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, UserDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userid", item.getId() + "");
                        bundle.putInt("lookhead", item.getLookhead());
                        bundle.putInt("numuser", item.getNumuser());

                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });

    }


}
