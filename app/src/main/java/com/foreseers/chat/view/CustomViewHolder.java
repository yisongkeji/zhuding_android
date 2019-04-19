package com.foreseers.chat.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.BannerData;
import com.ms.banner.holder.BannerViewHolder;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/28
 */
public class CustomViewHolder implements BannerViewHolder<BannerData> {

    private TextView banner_text1;
    private TextView banner_text2;
    private ImageView img;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_page, null);

        img = (ImageView) view.findViewById(R.id.img);
        banner_text1 = (TextView) view.findViewById(R.id.banner_text1);
        banner_text2 = (TextView) view.findViewById(R.id.banner_text2);
        return view;
    }

    @Override
    public void onBind(Context context, int position, BannerData data) {
        banner_text1.setText(data.getText1());
        banner_text2.setText(data.getText2());
        img.setImageResource(data.getUrl());
    }

}