package com.foreseers.chat.global;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.foreseers.chat.R;



/**
 *
 */

public abstract class BaseMainFragment extends BaseFragment {
    public FragmentTransaction mFragmentTransaction;
    public FragmentManager fragmentManager;
    public String curFragmentTag = "";

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    public final int DATASUCCESS = 1;
    public final int DATAFELLED = 0;
    public final int USERHEADSUCCESS = 2;
    public final int USERIMGSUCCESS = 3;
    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
            //MySuperToast.myBottomToast(_mActivity, "再次点击退出");
        }
        return true;
    }
}
