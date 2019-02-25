package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.foreseers.chat.foreseers.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

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
    @BindView(R.id.text_add_friend)
    TextView textAddFriend;
    private Context context;
    private int num;
    private String name;
    private String userName;
    private LeaveMyDialogListener listener;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public AddFriendDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddFriendDialog(Context context, int theme, String name,String userName, int num,
                           LeaveMyDialogListener listener) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.name = name;
        this.num = num;
        this.userName=userName;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_friend_dialog);
        ButterKnife.bind(this);


        textAddFriend.setText(context.getString(R.string.my_add_friend).replace("name", name).replace("num", num + ""));
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
                //参数为要添加的好友的username和添加理由
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().addContact(userName, "");
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    String s1 = context.getResources().getString(R.string
                                            .send_successful);
                                    Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (final Exception e) {

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    String s2 = context.getResources().getString(R.string
                                            .Request_add_buddy_failure);
                                    Toast.makeText(getApplicationContext(), s2 + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                            Log.e("huanxin", "run: "+e.toString() );
                        }
                    }
                }).start();


                layoutSend.setVisibility(View.GONE);
                layoutSendSuccess.setVisibility(View.VISIBLE);

//                layoutSend.setVisibility(View.GONE);
//                layoutSendSuccess.setVisibility(View.VISIBLE);
//                textSendSeccess.setText(R.string.send_defeated);
                break;


            case R.id.dialog_send_success_cancel:
                listener.onClick(view);
                break;
            default:
                break;

        }
    }

}
