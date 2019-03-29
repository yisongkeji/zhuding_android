package com.foreseers.chat.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.util.GlideUtil;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/26
 */
public class ImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Activity context;

    public ImgAdapter(Activity context, @Nullable List<String> data) {
        super(R.layout.item_img, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        GlideUtil.glide(context, item, (ImageView) helper.getView(R.id.item_img).findViewById(R.id.item_img));
    }
}
