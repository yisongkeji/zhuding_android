package com.foreseers.chat.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.foreseers.R;

import java.util.List;

public class ImgPagerAdapter extends PagerAdapter {
    private Activity mContext;
    private List<String> mData;
    public ImgPagerAdapter(Activity context, List<String> list){
        this.mContext=context;
        this.mData=list;

    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_imgpager,null);
//        TextView tv = (TextView) view.findViewById(R.id.tv);
//        tv.setText(mData.get(position));
        ImageView imageView=view.findViewById(R.id.img_imgpager);

        Glide.with(mContext).load(mData).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return 0;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }
}
