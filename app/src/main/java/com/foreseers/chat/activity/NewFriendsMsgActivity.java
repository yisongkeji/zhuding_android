package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.foreseers.chat.R;
import com.foreseers.chat.adapter.NewFriendsMsgAdapter;
import com.foreseers.chat.db.InviteMessgeDao;
import com.foreseers.chat.domain.InviteMessage;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.view.widget.MyTitleBar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewFriendsMsgActivity extends BaseActivity {

    @BindView(R.id.my_titlebar) MyTitleBar myTitlebar;
    @BindView(R.id.list) ListView listView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_new_friends_msg);
        ButterKnife.bind(this);

        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        Collections.reverse(msgs);

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {

        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                setResult(0x001, intent);
                finish();
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            intent = new Intent();
            setResult(0x001, this.intent);
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
