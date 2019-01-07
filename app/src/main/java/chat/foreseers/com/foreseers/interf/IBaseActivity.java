package chat.foreseers.com.foreseers.interf;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

/**
 *
 */

public interface IBaseActivity {
    /**
     * 获取上下文
     */
    public AppCompatActivity getActivity();
    /**
     * 获取handler
     */
    public Handler getHandler();
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
     * 处理handler消息
     */
    public void processHandlerMessage(Message msg);
}
