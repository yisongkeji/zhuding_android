package com.foreseers.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.foreseers.chat.activity.ImgAlbumActivity;
import com.foreseers.chat.foreseers.R;
import com.foreseers.chat.fragment.MyFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    Activity context;
    Fragment fragment;
    private HashMap<Integer, float[]> xyMap = new HashMap<Integer, float[]>();//所有子项的坐标
    private int screenWidth;//屏幕宽度
    private int screenHeight;//屏幕高度

    List<String> data;

    public AlbumAdapter(Activity context, Fragment fragment, @Nullable ArrayList<String> data) {
        super(R.layout.item_album, data);
        this.context = context;
        this.fragment = fragment;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

    }

    private static final int REQUEST_CODE_DELETEIMG = 201;

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        data = getData();
        Glide.with(context).load(item.toString()).error(R.mipmap.icon_me_loading_02).into(
                (ImageView) helper.getView(R.id.img_album));

        helper.getView(R.id.img_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "onClick helper.getAdapterPosition(): " + helper.getAdapterPosition());
                for (int j = 0; j < data.size(); j++) {
                    float[] xyf = new float[]{screenWidth / 2, 0};
                    xyMap.put(helper.getAdapterPosition(), xyf);
                }

                Intent intent = new Intent(mContext, ImgAlbumActivity.class);
                intent.putStringArrayListExtra("urls", (ArrayList<String>) data);
                intent.putExtra("position", helper.getAdapterPosition());
                intent.putExtra("xyMap", xyMap);
                Log.i(TAG, "data: " + data.toString());
                fragment.startActivityForResult(intent, REQUEST_CODE_DELETEIMG);


//                Intent intent = new Intent(context, ImgAlbumActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("data", item);
//                bundle.putInt("position", helper.getAdapterPosition());
//                intent.putExtras(bundle);
//                Log.i("ImgAlbumActivity@@@@@11", " helper.getAdapterPosition()" +  helper
// .getAdapterPosition());
//                context.startActivity(intent);

            }
        });


    }
}
