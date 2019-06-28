package com.foreseers.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.foreseers.chat.R;
import com.foreseers.chat.adapter.DailyQuestionAdapter;
import com.foreseers.chat.adapter.ScrollLinearLayoutManager;
import com.foreseers.chat.bean.DailyQuestionBean;
import com.foreseers.chat.global.BaseActivity;
import com.foreseers.chat.view.widget.MyTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyQuestionActivity extends BaseActivity {

    @BindView(R.id.my_titlebar)
    MyTitleBar myTitlebar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private DailyQuestionAdapter dailyQuestionAdapter;
    private List<DailyQuestionBean> lists;
    private ScrollLinearLayoutManager scrollLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_daily_question);
        ButterKnife.bind(this);
        initRv();
    }


    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {
        myTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    private void initRv() {
        lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            lists.add(new DailyQuestionBean());
        }
        scrollLinearLayoutManager=new ScrollLinearLayoutManager(this);
        dailyQuestionAdapter = new DailyQuestionAdapter();
        recyclerview.setLayoutManager(scrollLinearLayoutManager);
        recyclerview.setAdapter(dailyQuestionAdapter);
        dailyQuestionAdapter.setNewData(lists);
        dailyQuestionAdapter.setRvCanScrollImp(new DailyQuestionAdapter.RvCanScrollImp() {
            @Override
            public void setRvCanScroll(boolean isCanRvScroll) {
                if (isCanRvScroll) {
                    scrollLinearLayoutManager.setmCanVerticalScroll(true);
                } else {
                    scrollLinearLayoutManager.setmCanVerticalScroll(false);
                }
            }
        });

    }

}
