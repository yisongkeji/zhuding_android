package chat.foreseers.com.foreseers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import chat.foreseers.com.foreseers.R;

public class AddFriendDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.dialog_cancel)
    ImageButton dialogCancel;
    @BindView(R.id.dialog_send)
    ImageButton dialogSend;
    @BindView(R.id.layout_send)
    FrameLayout layoutSend;
    @BindView(R.id.layout_send_success)
    FrameLayout layoutSendSuccess;
    @BindView(R.id.text_send_seccess)
    TextView textSendSeccess;
    @BindView(R.id.dialog_send_success_cancel)
    ImageButton dialogSendSuccessCancel;
    private Context context;
    private LeaveMyDialogListener listener;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public AddFriendDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddFriendDialog(Context context, int theme, LeaveMyDialogListener listener) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_friend_dialog);
        ButterKnife.bind(this);
        layoutSend.setVisibility(View.VISIBLE);
        layoutSendSuccess.setVisibility(View.GONE);
        dialogCancel.setOnClickListener(this);
        dialogSend.setOnClickListener(this);
        dialogSendSuccessCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_cancel:
                listener.onClick(view);
                break;
            case R.id.dialog_send:

                int i=1;
                switch (i){
                    case 0:
                        layoutSend.setVisibility(View.GONE);
                        layoutSendSuccess.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        layoutSend.setVisibility(View.GONE);
                        layoutSendSuccess.setVisibility(View.VISIBLE);
                        textSendSeccess.setText(R.string.send_defeated);
                        break;
                        default:
                            break;

                }

                break;
            case R.id.dialog_send_success_cancel:
                listener.onClick(view);
                break;
            default:
                break;

        }
    }

}
