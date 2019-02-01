package com.foreseers.chat.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.activity.ImgAlbumActivity;
import com.foreseers.chat.bean.AlbumBean;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    Activity context;


    public AlbumAdapter(Activity context, @Nullable ArrayList<String> data) {
        super(R.layout.item_album, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {

        Glide.with(context).load(item.toString()).error(R.mipmap.icon_me_loading_02).into((ImageView) helper.getView(R.id.img_album));

        helper.getView(R.id.img_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImgAlbumActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("data", item);
                bundle.putInt("position", helper.getAdapterPosition());
intent.putExtras(bundle);
                Log.i("ImgAlbumActivity@@@@@11", "datalist onClick: " + helper.getAdapterPosition());
                context.startActivity(intent);


            }
        });


    }
}
