package com.foreseers.chat.foreseers.interf;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

/**
 *
 */

public interface IBaseFragment {
    /**
     * 初始化视图
     */
    public void initViews();

    /**
     * 初始化数据
     */
    public void initDatas();

    /**
     * 设置监听器
     */
    public void installListeners();
    /**
     * 获取上下文
     */
    public Activity getActivity();
    /**
     * 获取handler
     */
    public Handler getHandler();
    /**
     * 处理handler消息
     */
    public void processHandlerMessage(Message msg);
}

