package com.foreseers.chat.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.FortunetellingListBean;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/1
 */
public class FortunetellingListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private  Context context;
    public FortunetellingListAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.item_fortunetellinglist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.text,item);

    }
}
