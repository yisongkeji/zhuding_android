package com.foreseers.chat.util;

import android.view.View;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/16
 */
public abstract class CustomClickListener implements View.OnClickListener {
    private long mLastClickTime;
    private long timeInterval = 1000L;

    public CustomClickListener() {

    }

    public CustomClickListener(long interval) {
        this.timeInterval = interval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick();
            mLastClickTime = nowTime;
        } else {
            // 快速点击事件
            onFastClick();
        }
    }

    protected abstract void onSingleClick();
    protected abstract void onFastClick();
}