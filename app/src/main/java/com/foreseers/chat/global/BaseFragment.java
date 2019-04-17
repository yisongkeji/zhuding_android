package com.foreseers.chat.global;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foreseers.chat.interf.IBaseFragment;

import me.yokeyword.fragmentation.SupportFragment;
/**
 *
 */

public abstract class BaseFragment extends SupportFragment implements IBaseFragment {
    private Handler mHandler;
    private Activity mContext;
    public final int DATASUCCESS = 1;
    public final int DATAFELLED = 0;
    public Activity getContext(){
        return mContext;
    }
    public void initFragment() {
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                processHandlerMessage(msg);
            }
        };

        initViews();
        initDatas();
        installListeners();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity){
        mContext = activity;
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment();
    }

    @Override
    abstract public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public Handler getHandler() {
        return mHandler;
    }



}
