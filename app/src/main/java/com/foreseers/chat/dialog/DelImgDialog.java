package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.foreseers.chat.foreseers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DelImgDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.button_ok)
    Button buttonOk;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    private Context context;
    private LeaveMyDialogListener listener;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public DelImgDialog(Context context) {
        super(context);
        this.context = context;
    }

    public DelImgDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_del_img);
        ButterKnife.bind(this);
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//
            case R.id.button_ok:
                listener.onClick(view);
                break;
            case R.id.button_cancel:
                listener.onClick(view);
                break;
//
        }
    }

}
