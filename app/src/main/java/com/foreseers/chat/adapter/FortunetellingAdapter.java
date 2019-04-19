package com.foreseers.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.activity.FortunetellingListActivity;
import com.foreseers.chat.bean.FortunetellingBean;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/28
 */
public class FortunetellingAdapter extends BaseQuickAdapter<FortunetellingBean.DataBean, BaseViewHolder> {
    private Context context;
    public FortunetellingAdapter(Context context, @Nullable List<FortunetellingBean.DataBean> data) {
        super(R.layout.item_fortunetelling, data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, final FortunetellingBean.DataBean item) {

        Log.d(TAG, "convert: item.getColour()" + item.getColour());
        CardView cardView = (CardView) helper.getView(R.id.card_View)
                .findViewById(R.id.card_View);
        cardView.setBackgroundResource(item.getColour());
        helper.setText(R.id.text_price,item.getPrice())
                .setText(R.id.text_type, (item.getSign() == 1) ?context.getString(R.string.free_unlock) :context.getString(R.string.free_buy)  )
                .setText(R.id.text_content, (item.getSign() == 1) ? context.getResources()
                        .getString(R.string.text_lifebook_content3)
                        .replace("&", item.getSize() + ""):context.getResources()
                        .getString(R.string.text_lifebook_content2)
                        .replace("&", item.getSize() + "") );
        Glide.with(context).load((item.getSign() == 1) ?R.mipmap.icon_lock_02:R.mipmap.icon_lock_01).into((ImageView) helper.getView(R.id.img));

        helper.getView(R.id.img_title).setBackgroundResource(item.getImgtitle());
        if (helper.getAdapterPosition()!=0){
            Log.e(TAG, "convert: "+item.getImgcontent());
            helper.getView(R.id.img_content).setBackgroundResource(item.getImgcontent());
        }

        if (item.getName()
                .equals("自身")) {
            helper.setText(R.id.text_type, context.getString(R.string.free_charge))
                    .setText(R.id.text_content, context.getResources()
                            .getString(R.string.text_lifebook_content1)
                            .replace("&", item.getSize() + ""));
        }





    }

}
