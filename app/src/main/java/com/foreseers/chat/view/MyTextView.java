package com.foreseers.chat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.foreseers.chat.global.MyApplication;

/**
 * File description.
 *
 * @author how
 * @date 2019/4/10
 */
public class MyTextView   extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context) {
        super(context);
        setTypeface(MyApplication.getTypeface());
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(MyApplication.getTypeface());
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(MyApplication.getTypeface());
    }

}
