package com.foreseers.chat.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.bean.ChatBean;

public class ChatAdapter extends BaseQuickAdapter <ChatBean,BaseViewHolder>{

    Activity context;

    public ChatAdapter(Activity context, @Nullable List<ChatBean> data) {
        super(R.layout.item_chat, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ChatBean chatItemBean) {

        baseViewHolder.setText(R.id.item_chat_name,chatItemBean.getName())
        .setText(R.id.text_time,"14:50");
    }
}
