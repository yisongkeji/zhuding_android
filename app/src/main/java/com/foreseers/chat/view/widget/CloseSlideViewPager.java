package com.foreseers.chat.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * File description.
 * 可关闭滑动的viewpager
 * @author how
 * @date 2019/7/9
 */
public class CloseSlideViewPager extends ViewPager {

    public CloseSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CloseSlideViewPager(@NonNull Context context) {
        super(context);
    }

    private boolean isPagingEnabled = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }


}
