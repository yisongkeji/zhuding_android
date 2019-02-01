package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseers.chat.foreseers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendErrorDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.text_send_seccess)
    TextView textSendSeccess;
    @BindView(R.id.dialog_send_error)
    ImageButton dialogSendError;
    @BindView(R.id.layout_send_success)
    FrameLayout layoutSendSuccess;
    @BindView(R.id.layout_add_friend_error)
    LinearLayout layoutAddFriendError;
    private Context context;
    private LeaveMyDialogListener listener;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public AddFriendErrorDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddFriendErrorDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_add_friend_error);
        ButterKnife.bind(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_add_friend_error:
                listener.onClick(view);
                break;
            case R.id.dialog_send_error:
                listener.onClick(view);
                break;

        }
    }
}
