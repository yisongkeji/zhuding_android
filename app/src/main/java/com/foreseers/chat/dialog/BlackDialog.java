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

public class BlackDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.button_ok)
    Button buttonOk;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    private Context context;
    private LeaveMyDialogListener listener;

    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public BlackDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BlackDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_del_friend);
        ButterKnife.bind(this);
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        textTitle.setText("是否拉黑对方？");
        text.setText("拉黑后将删除对方所有消息并不再接收任何新消息");
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
