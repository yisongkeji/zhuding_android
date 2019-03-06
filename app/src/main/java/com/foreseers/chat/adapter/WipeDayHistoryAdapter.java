package com.foreseers.chat.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.foreseers.chat.R;
import com.foreseers.chat.bean.WipeDayHistoryBean;

import java.util.List;

public class WipeDayHistoryAdapter extends BaseQuickAdapter<WipeDayHistoryBean.DataBean ,BaseViewHolder> {
    Activity context;
    public WipeDayHistoryAdapter(Activity context, @Nullable List<WipeDayHistoryBean.DataBean> data) {
        super( R.layout.item_section_content, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WipeDayHistoryBean.DataBean item) {

        Glide.with(context).load(item.getHead()).error(R.mipmap.icon_me_loading_03).placeholder(R.mipmap.icon_me_loading_03).into((ImageView) helper.getView(R.id.image_people));


        helper.setText(R.id.text_people_name,item.getUsername());
        helper.setText(R.id.text_people_progress,item.getUserscore()+"");
        helper.setText(R.id.text_people_ziwei,item.getZiwei());


        switch (item.getSex()) {
            case "F":
                helper.getView(R.id.text_people_sex).setBackgroundResource(R.drawable
                        .rounded_layout_pink);
                helper.setText(R.id.text_people_sex, ("♀" + item.getAge()));

                break;
            case "M":
                helper.getView(R.id.text_people_sex).setBackgroundResource(R.drawable
                        .rounded_layout_blue);
                helper.setText(R.id.text_people_sex, ("♂" + item.getAge()));

                break;

            default:
                break;
        }

    }
}
