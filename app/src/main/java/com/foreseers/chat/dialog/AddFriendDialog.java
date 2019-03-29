package com.foreseers.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.foreseers.chat.R;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class AddFriendDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.button_ok)
    Button buttonOk;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    private Context context;
    private int num;
    private String name;
    private String userName;
    private LeaveMyDialogListener listener;
    private String avatar;


    public interface LeaveMyDialogListener {
        public void onClick(View view);
    }

    public AddFriendDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddFriendDialog(Context context, int theme, String name, String userName, String avatar, int num, LeaveMyDialogListener listener) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.name = name;
        this.num = num;
        this.userName = userName;
        this.listener = listener;
        this.avatar = avatar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_friend_dialog);
        ButterKnife.bind(this);

        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                listener.onClick(view);
                break;
            case R.id.button_ok:
                //参数为要添加的好友的username和添加理由
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().addContact(userName, name + "|" + avatar);
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
                                    Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.e("huanxin", "run: " + e.toString());
                        }
                    }
                }).start();

                listener.onClick(view);
                break;



            default:
                break;

        }
    }

}
