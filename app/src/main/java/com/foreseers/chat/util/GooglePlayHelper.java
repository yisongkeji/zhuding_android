package com.foreseers.chat.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class GooglePlayHelper {
    public static final String TAG = "google支付";

    public static final String ITEM_TYPE_INAPP = "inapp";//应用内

    public static final String product1 = ".vip360";//商品
    public static final String product2 = ".vip90";//商品
    public static final String product3 = ".vip30";//商品
    public static final String item1 = ".item1";//商品擦子30
    public static final String item2 = ".item2";//商品擦子90
    public static final String item3 = ".item3";//商品擦子200


    public static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArwTM77tMi5h3e3lzqL6nT0jCCpVgRyvVLt/eycfVSzEMdrX45AIgKKj8RE6D2QbalniMV7aEHyW5q0aOOpEwVsdR0XjYu56hbY6uk/eR+PFzmtWHG7nr9HGxQ6rCoMqUXfx2XncDFJrYDMdikEkOS928I9Bm5KMo3f4v/VynRs5otkF5UxBSA1vJTBeHPp3jhYcIgTzdGYed3lp1fqeYQ4d+HAodsRJrjBPVBX9qCQJyNE7T/lP7dqYgj+tCDYTDRb93rL2DJZBtAGP6ahnIFuNE6y9ApsMOqeIZoG1k54OkeARrPCxaemTvBIOkP1WlwCYT60Ip1TRUUiB0QS+PNwIDAQAB";

    public static final String developerPayload = "";//自定义


    public static boolean checkApkExist(Context context, String packageName){
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                                        PackageManager.GET_UNINSTALLED_PACKAGES);
            Log.d(TAG,info.toString()); // Timber 是我打印 log 用的工具，这里只是打印一下 log
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG,e.toString()); // Timber 是我打印 log 用的工具，这里只是打印一下 log
            return false;
        }
    }

    public static boolean isGooglePlayServiceAvailable (Context context) {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (status == ConnectionResult.SUCCESS) {
            Log.e("YXH", "GooglePlayServicesUtil service is available.");
            return true;
        } else {
            Log.e("YXH", "GooglePlayServicesUtil service is NOT available.");
            return false;
        }
    }



}
