package com.foreseers.chat.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

public class MediaService extends Service {


    private long triggerTime;

    public MediaService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*每次调用startService启动该服务都会执行*/
    public int onStartCommand(Intent intent, int flags, int startId) {
        int type=intent.getIntExtra("type",0);
        int hour=intent.getIntExtra("hour",0);
        String userid = intent.getStringExtra("userid");
        String friendid = intent.getStringExtra("friendid");

        Log.d("TAG@@@", "启动服务：" + new Date().toString()+"   hour:"+hour);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch (type){
            case 0:
                triggerTime = SystemClock.elapsedRealtime() +(8-hour)*3600000;
                break;
            case 1:
                triggerTime = SystemClock.elapsedRealtime() +(24-hour)*3600000;
                break;
            case 2:
                 triggerTime = SystemClock.elapsedRealtime() +(72-hour)*3600000;
                break;
        }

        long triggerTime = SystemClock.elapsedRealtime() + 1500;
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra("friendid",friendid);
        i.putExtra("userid",userid);
        i.putExtra("type",type);
        PendingIntent pi = PendingIntent.getBroadcast(this, type, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        Log.d("TAG@@@", "跳转@@@@@@@@@@@@@@@@@@@@@@" );
        return super.onStartCommand(intent, flags, startId);
    }

}
