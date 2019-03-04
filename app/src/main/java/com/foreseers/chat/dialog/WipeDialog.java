package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.foreseers.chat.R;

import butterknife.ButterKnife;

public class WipeDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private LeaveMyDialogListener listener;



    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public WipeDialog(Context context) {
        super(context);
        this.context = context;
    }

    public WipeDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_del_img);
        ButterKnife.bind(this);

    }
    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//
//            case R.i
//
//
//        }
    }

}
