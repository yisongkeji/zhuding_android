package com.foreseers.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.foreseers.chat.R;
import com.foreseers.chat.fragment.LookHistoryFragment;
import com.foreseers.chat.fragment.WipeHistoryFragment;
import com.foreseers.chat.global.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WipeHistoryActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radiobutton1) RadioButton radiobutton1;
    @BindView(R.id.radiobutton2) RadioButton radiobutton2;
    @BindView(R.id.radiogroup) RadioGroup radiogroup;
    @BindView(R.id.layout_frame) FrameLayout layoutFrame;
    @BindView(R.id.text) TextView text;
    @BindView(R.id.layout) FrameLayout layout;
    @BindView(R.id.text_ok) TextView textOk;
    private WipeHistoryFragment wipeHistoryFragment;
    private FragmentManager fragmentManager;

    private LookHistoryFragment lookHistoryFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wipe_history);
        ButterKnife.bind(this);

        radiobutton1.setChecked(true);
        radiogroup.setOnCheckedChangeListener(this);

        int vip = getIntent().getIntExtra("vip", 0);
        switch (vip) {
            case 0:
                layout.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                break;
            case 1:
                layout.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radiobutton1:
                transaction = fragmentManager.beginTransaction();

                if (lookHistoryFragment != null) {
                    transaction.hide(lookHistoryFragment);
                }
                if (wipeHistoryFragment == null) {
                    wipeHistoryFragment = new WipeHistoryFragment();
                    transaction.add(R.id.layout_frame, wipeHistoryFragment);
                }
                transaction.show(wipeHistoryFragment);
                transaction.commit();
                break;

            case R.id.radiobutton2:
                transaction = fragmentManager.beginTransaction();

                if (wipeHistoryFragment != null) {
                    transaction.hide(wipeHistoryFragment);
                }
                if (lookHistoryFragment == null) {
                    lookHistoryFragment = new LookHistoryFragment();
                    transaction.add(R.id.layout_frame, lookHistoryFragment);
                }
                transaction.show(lookHistoryFragment);
                transaction.commit();
                break;
            default:
                break;
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void initViews() {
        wipeHistoryFragment = new WipeHistoryFragment();

        lookHistoryFragment = new LookHistoryFragment();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.layout_frame, wipeHistoryFragment);
        transaction.add(R.id.layout_frame, lookHistoryFragment);
        transaction.hide(lookHistoryFragment)
                .show(wipeHistoryFragment);
        transaction.commit();
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void installListeners() {
        Log.i("@#@#@#@#@#", "installListeners: ");
    }

    @Override
    public void processHandlerMessage(Message msg) {

    }

    @OnClick(R.id.text_ok)
    public void onViewClicked() {

        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("position" ,3);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent
                .FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    //    private void changeFragment(int currentPosition) {
    //        transaction = getSupportFragmentManager().beginTransaction();
    //        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
    //        transaction.commit();
    //    }
}
