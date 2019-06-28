package com.foreseers.chat.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Ocean on 2019/6/22.
 */

public class ScrollLinearLayoutManager extends LinearLayoutManager {

    private boolean mCanVerticalScroll = true;

    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        if (!mCanVerticalScroll){
            return false;
        }else {
            return super.canScrollVertically();
        }
    }

    public void setmCanVerticalScroll(boolean b){
        mCanVerticalScroll = b;
    }


}
