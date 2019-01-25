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
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putString(EaseConstant.EXTRA_USER_ID, userid );
        args.putString("userName", username);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.layout, chatFragment).commit();
    }


}
