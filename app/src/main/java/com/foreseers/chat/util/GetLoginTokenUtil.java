package com.foreseers.chat.util;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class GetLoginTokenUtil {


    public static SharedPreferences getSP(Activity activity) {
        SharedPreferences userInfo = activity.getSharedPreferences("loginToken", MODE_PRIVATE);

        return userInfo;
    }

    public static String getUserId(Activity activity) {

        String userid = getSP(activity).getString("huanXinId", "");

        return userid;
    }

    public static String getFaceBookId(Activity activity) {

        String getFaceBookId = getSP(activity).getString("token", "");

        return getFaceBookId;
    }
}
