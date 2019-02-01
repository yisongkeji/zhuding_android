package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.foreseers.chat.foreseers.R;

public class DoubtDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.bt_know)
    Button btKnow;
    private Context context;
    private LeaveMyDialogListener listener;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public DoubtDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public DoubtDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_doubt);
        ButterKnife.bind(this);
        btKnow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_know:
                listener.onClick(view);
                break;

        }
    }

}
