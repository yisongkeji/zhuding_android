package chat.foreseers.com.foreseers.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import chat.foreseers.com.foreseers.R;
import chat.foreseers.com.foreseers.bean.ChatBean;

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
