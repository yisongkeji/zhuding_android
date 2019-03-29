package com.foreseers.chat.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceManager {
    public static final String PREFERENCE_NAME = "saveInfo";
    private static SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferencemManager;
    private static SharedPreferences.Editor editor;

    private PreferenceManager(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }
    public static synchronized void init(Context cxt){
        if(mPreferencemManager == null){
            mPreferencemManager = new PreferenceManager(cxt);
        }
    }

    /**
     * get instance of PreferenceManager
     *
     * @param
     * @return
     */
    public synchronized static PreferenceManager getInstance() {
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init first!");
        }

        return mPreferencemManager;
    }


    public void setHeadImgUrl(String url){
        editor.putString("HeadImgUrl",url);
        editor.apply();
    }
    public String getHeadImgUrl(){
        return mSharedPreferences.getString("HeadImgUrl",null);
    }

    public static SharedPreferences getSP(Context activity) {
        SharedPreferences userInfo = activity.getSharedPreferences("loginToken", MODE_PRIVATE);

        return userInfo;
    }

    public static String getUserId(Context activity) {

        String userid = getSP(activity).getString("huanXinId", "");

        return userid;
    }

    public static String getFaceBookId(Activity activity) {

        String getFaceBookId = getSP(activity).getString("token", "");

        return getFaceBookId;
    }
}
