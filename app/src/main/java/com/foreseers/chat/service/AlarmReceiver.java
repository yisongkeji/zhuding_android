package com.foreseers.chat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private Intent i;

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", 0);
        String userid = intent.getStringExtra("userid");
        String friendid = intent.getStringExtra("friendid");

        switch (type) {
            case 0://查看清晰头像
                Log.d("TAG@@@", "AlarmReceiver服务结束：HeadTimeService");
                i = new Intent(context, HeadTimeService.class);
                break;

            case 1://聊天发送图片
                Log.d("TAG@@@", "AlarmReceiver服务结束：ImgsTimeService");
                i = new Intent(context, ImgsTimeService.class);
                break;

            case 2://查看相册
                Log.d("TAG@@@", "AlarmReceiver服务结束：AlbumTimeService");
                i = new Intent(context, AlbumTimeService.class);
                break;
            default:
                break;
        }

        i.putExtra("friendid", userid);
        i.putExtra("userid", friendid);
        context.startService(i);

    }
}