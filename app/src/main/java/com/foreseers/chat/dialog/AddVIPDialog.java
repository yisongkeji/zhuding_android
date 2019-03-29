package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.foreseers.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddVIPDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.layout_vip1)
    LinearLayout layoutVip1;
    @BindView(R.id.layout_vip2)
    LinearLayout layoutVip2;
    @BindView(R.id.layout_vip3)
    LinearLayout layoutVip3;
    @BindView(R.id.button_vip)
    Button buttonVip;
    private Context context;
    private LeaveMyDialogListener listener;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public AddVIPDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddVIPDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_add_vip);
        ButterKnife.bind(this);
        layoutVip1.setOnClickListener(this);
        layoutVip2.setOnClickListener(this);
        layoutVip3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.layout_vip1:
                layoutVip1.setBackgroundResource(R.drawable.background_vip2);
                layoutVip2.setBackgroundResource(0);
                layoutVip3.setBackgroundResource(0);
                break;
            case R.id.layout_vip2:
                layoutVip1.setBackgroundResource(0);
                layoutVip2.setBackgroundResource(R.drawable.background_vip2);
                layoutVip3.setBackgroundResource(0);
                break;
            case R.id.layout_vip3:
                layoutVip1.setBackgroundResource(0);
                layoutVip2.setBackgroundResource(0);
                layoutVip3.setBackgroundResource(R.drawable.background_vip2);
                break;

        }
    }
}
