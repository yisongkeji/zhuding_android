package chat.foreseers.com.foreseers.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.UserDetailsActivity;
import chat.foreseers.com.foreseers.bean.PeopleBean;

public class PeopleAdapter extends BaseQuickAdapter<PeopleBean, BaseViewHolder> {

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

    public PeopleAdapter(Activity context, @Nullable List<PeopleBean> data) {
        super(R.layout.item_people, data);
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "华康华综体W5-B5.TTF");
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PeopleBean item) {

        baseViewHolder.setTypeface(typeface);
        baseViewHolder.setText(R.id.text_people_progress, "匹配度80%")
                .setText(R.id.text_people_sex, "男")
                .setText(R.id.text_people_age, "20歲")
                .setText(R.id.text_people_location, "新界");

        baseViewHolder.getView(R.id.image_people).findViewById(R.id.image_people).setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,UserDetailsActivity.class);
                context.startActivity(intent);
            }
        });

    }


}
