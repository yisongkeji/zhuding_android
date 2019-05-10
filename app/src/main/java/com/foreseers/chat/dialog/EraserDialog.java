package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foreseers.chat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EraserDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.text_ok) TextView textOk;
    @BindView(R.id.img_finish) ImageView imgFinish;
    @BindView(R.id.cardView) CardView cardView;
    @BindView(R.id.layout) LinearLayout layout;
    private Context context;
    private LeaveMyDialogListener listener;

    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public EraserDialog(Context context) {
        super(context);
        this.context = context;
    }

    public EraserDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_eraser);
        ButterKnife.bind(this);
        textOk.setOnClickListener(this);
        imgFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_ok:
                listener.onClick(view);
                break;
            case R.id.img_finish:
                listener.onClick(view);
                break;
            default:
                break;
        }
    }


//    public void show(){
//        cardView.setVisibility(View.GONE);
//        layout.setVisibility(View.VISIBLE);
//    }


}
