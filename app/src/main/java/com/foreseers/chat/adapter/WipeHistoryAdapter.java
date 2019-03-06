package com.foreseers.chat.adapter;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.bean.HistoryBean;
import com.foreseers.chat.bean.MySection;
import com.foreseers.chat.bean.Video;
import com.foreseers.chat.R;

import java.util.List;

public class WipeHistoryAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    private Activity context;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public WipeHistoryAdapter(Activity activity, List data) {
        super( R.layout.item_section_content, R.layout.def_section_head, data);
        this.context=activity;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        Video video = (Video) item.t;
        Glide.with(context).load(video.getHead()).into((ImageView) helper.getView(R.id.image_people));


        helper.setText(R.id.text_people_name,video.getUsername());
        helper.setText(R.id.text_people_progress,video.getUserscore()+"");
        helper.setText(R.id.text_people_ziwei,video.getZiwei());


        switch (video.getSex()) {
            case "F":
                helper.getView(R.id.text_people_sex).setBackgroundResource(R.drawable
                        .rounded_layout_pink);
                helper.setText(R.id.text_people_sex, ("♀" + video.getAge()));

                break;
            case "M":
                helper.getView(R.id.text_people_sex).setBackgroundResource(R.drawable
                        .rounded_layout_blue);
                helper.setText(R.id.text_people_sex, ("♂" + video.getAge()));

                break;

            default:
                break;
        }

    }



}
