package com.foreseers.chat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.foreseers.chat.R;
import com.foreseers.chat.adapter.AlbumAdapter;
import com.foreseers.chat.fragment.MyFragment;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        initView();
    }

    private void initView() {

    }
}
