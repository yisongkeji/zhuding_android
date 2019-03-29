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

public class InformDialog  extends Dialog implements View.OnClickListener {

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
    private int type;

    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public InformDialog(Context context) {
        super(context);
        this.context = context;
    }

    public InformDialog(Context context,int type, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
        this.type=type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_del_friend);
        ButterKnife.bind(this);
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        switch (type){
            case 1:
                textTitle.setText("是否移出黑名单");
                text.setText("移出黑名单也不会恢复好友关系");
                break;
            case 2:
                textTitle.setText("是否清空临时会话记录");
                text.setText("将删除临时聊天记录");
                break;
            case 3:
                textTitle.setText("是否清空好友聊天记录");
                text.setText("将删除所有好友的聊天记录");
                break;
            case 4:
                textTitle.setText("是否清理缓存");
                text.setText("将删除缓存");
                break;
        }

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
