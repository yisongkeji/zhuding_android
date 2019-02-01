package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.foreseers.chat.foreseers.R;


public class NoFriendNumberDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.dialog_no_friend_number)
    LinearLayout dialogNoFriendNumber;
    private Context context;
    private LeaveMyDialogListener listener;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public NoFriendNumberDialog(Context context) {

        super(context);
        this.context = context;
    }

    public NoFriendNumberDialog(Context context, int theme, LeaveMyDialogListener
            listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_no_friend_number);
        ButterKnife.bind(this);
        dialogNoFriendNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.dialog_no_friend_number:
                listener.onClick(view);
                break;

        }
    }
}
