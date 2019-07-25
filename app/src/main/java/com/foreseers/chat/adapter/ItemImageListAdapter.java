package com.foreseers.chat.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.util.GlideUtil;

public class ItemImageListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ItemImageListAdapter() {
        super(R.layout.item_imglist);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtil.glide(mContext, item, (ImageView) helper.getView(R.id.imageView));
    }


}
