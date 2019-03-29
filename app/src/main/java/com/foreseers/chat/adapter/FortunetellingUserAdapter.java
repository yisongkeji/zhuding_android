package com.foreseers.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.foreseers.chat.R;
import com.foreseers.chat.activity.FortunetellingActivity;
import com.foreseers.chat.util.PreferenceManager;
import com.foreseers.chat.util.Urls;
import com.foreseers.chat.view.item.Level0Item;
import com.foreseers.chat.view.item.Level1Item;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * File description.
 *
 * @author how
 * @date 2019/3/22
 */
public class FortunetellingUserAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public FortunetellingUserAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.context = context;
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:

                final Level0Item lv0 = (Level0Item) item;
                holder.setText(R.id.text_title, lv0.title)
                        .setBackgroundRes(R.id.view, lv0.color);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        int positionAtAll = getParentPositionInAll(pos);
                        Log.d("@@@@@@@", positionAtAll+"Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                        if (pos==2){
                            OkGo.<String>post(Urls.Url_LifeBook).tag(this)
                                    .params("userid", PreferenceManager.getUserId(context))
                                    .params("name","郑皓")
                                    .params("date","1993-09-10")
                                    .params("time","12:00:00")
                                    .params("gender","M")
                                    .params("timezone","UTC+06:30")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                        }
                                    });
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.text_name, lv1.name)
                        .setText(R.id.text_time, lv1.age);

                holder.getView(R.id.content)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, FortunetellingActivity.class);
                                intent.putExtra("lifeuserid",lv1.lifeuserid);
                                context.startActivity(intent);
                            }
                        });

                holder.getView(R.id.right)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int pos = holder.getAdapterPosition();
                                //                        int positionAtAll = getParentPositionInAll(pos);
                                Log.d(TAG, "Level 0 item pos: " + pos);
                                remove(pos);

                                //                        if (positionAtAll !=0) {
                                ////                            IExpandable multiItemEntity = (IExpandable) getData().get(positionAtAll);
                                ////                            if (!hasSubItems(multiItemEntity)) {
                                ////                                remove(positionAtAll);
                                ////                            }
                                //                            remove(pos);
                                //                        }

                            }
                        });

                break;
        }
    }
}
