package com.foreseers.chat.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.foreseers.chat.R;
import com.youth.banner.loader.ImageLoader;

import java.io.File;

public class GlideUtil {

    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).placeholder(R.mipmap.icon_me_loading_03).dontAnimate().error(R.mipmap.default_image).into(imageView);
        }

    }

    public static void glide (Context context,String path,ImageView imageView){
        Glide.with(context).load(path).placeholder(R.mipmap.icon_me_loading_03).dontAnimate().error(R.mipmap.default_image).into(imageView);

    }
    public static void glideMatch (Context context,String path,ImageView imageView){
        Glide.with(context).load(path).placeholder(R.mipmap.icon_me_loading_03).diskCacheStrategy(DiskCacheStrategy.RESULT).dontAnimate().error(R.mipmap.icon_me_loading_03).into(imageView);

    }    public static void glideFile(Context context, File file, ImageView imageView){
        Glide.with(context).load(file).placeholder(R.mipmap.icon_me_loading_03).dontAnimate().error(R.mipmap.icon_me_loading_03).into(imageView);

    }
}
