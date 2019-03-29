package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.foreseers.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WipeDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.button_ok) Button buttonOk;
    @BindView(R.id.button_cancel) Button buttonCancel;
    @BindView(R.id.text_title) TextView textTitle;
    @BindView(R.id.text_content) TextView textContent;
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
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        textTitle.setText(context.getResources().getString(R.string.foreseers_clean_photo));
        textContent.setText(context.getResources().getString(R.string.foreseers_ensure_clean_photo));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_ok:
                listener.onClick(view);
                break;

            case R.id.button_cancel:
                listener.onClick(view);
                break;

            default:
                break;
        }
    }
}
