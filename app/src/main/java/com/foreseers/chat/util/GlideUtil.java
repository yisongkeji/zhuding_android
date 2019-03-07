package com.foreseers.chat.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.foreseers.chat.R;
import com.youth.banner.loader.ImageLoader;

public class GlideUtil {

    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).error(R.mipmap.default_image).placeholder(R.mipmap.default_image).into(imageView);
        }

    }

    public static void glide (Context context,String path,ImageView imageView){
        Glide.with(context).load(path).error(R.mipmap.default_image).placeholder(R.mipmap.default_image).into(imageView);

    }
    public static void glideMatch (Context context,String path,ImageView imageView){
        Glide.with(context).load(path).error(R.mipmap.icon_me_loading_03).placeholder(R.mipmap.icon_me_loading_03).into(imageView);

    }
}
