package com.foreseers.chat.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.R;
import com.foreseers.chat.bean.FortunetellingBean;

import java.util.List;

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
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FortunetellingBean.DataBean item) {

        Log.d(TAG, "convert: item.getColour()"+item.getColour());
        CardView cardView=(CardView)helper.getView(R.id.card_View).findViewById(R.id.card_View);
        cardView.setCardBackgroundColor(item.getColour());

        helper.setText(R.id.text_title,item.getName())
                .setText(R.id.text_type,(item.getSign()==1)?context.getString(R.string.free_buy):context.getString(R.string.free_unlock))
                .setText(R.id.text_type,(item.getName().equals("自身"))?context.getString(R.string.free_charge):context.getString(R.string.free_buy));

    }
}
