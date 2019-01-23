package chat.foreseers.com.foreseers.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.NetUtils;
import butterknife.ButterKnife;
import chat.foreseers.com.foreseers.R;

public class ChatActivity extends AppCompatActivity {


    private String userid;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString(EaseConstant.EXTRA_USER_ID);
        username = bundle.getString("username");
        Log.i("chatActivity", "userid"+userid+"````username"+username);
        initView();
    }

    private void initView() {
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();


        //传入参数
        Bundle args = new Bundle();
//        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userid );
        args.putString("userName", username);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.layout, chatFragment).commit();


//        //注册一个监听连接状态的listener
//        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(ChatActivity.this)) {
                            //连接不到聊天服务器
                        } else {
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }
}
