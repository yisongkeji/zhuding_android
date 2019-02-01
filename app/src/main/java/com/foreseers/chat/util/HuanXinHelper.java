package com.foreseers.chat.util;


import com.hyphenate.chat.EMClient;

public class HuanXinHelper {
    private static HuanXinHelper instance = null;
    public synchronized static HuanXinHelper getInstance() {
        if (instance == null) {
            instance = new HuanXinHelper();
        }
        return instance;
    }

    /**
     * if ever logged in
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }
}
