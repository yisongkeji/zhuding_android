package com.foreseers.chat.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Date;

public class MediaService extends Service {


    private long anHour;
    private String userid;
    private String friendid;

    public MediaService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*每次调用startService启动该服务都会执行*/
    public int onStartCommand(Intent intent, int flags, int startId) {
        int type = intent.getIntExtra("type", 0);
        int hour = intent.getIntExtra("hour", 0);
        userid = intent.getStringExtra("userid");
        friendid = intent.getStringExtra("friendid");

        Log.d("TAG@@@", "启动服务：" + new Date().toString() + "   hour:" + hour);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch (type) {
            case 0:
                anHour = 8* 3600000 - hour;
                Log.d("TAG@@@", "启动服务1：triggerTime--" + anHour);
                break;
            case 1:
                anHour = 24* 3600000 - hour;
                Log.d("TAG@@@", "启动服务2：triggerTime--" + anHour);
                refresh();
                break;
            case 2:
                anHour = 72* 3600000 - hour;
                Log.d("TAG@@@", "启动服务3：triggerTime--" + anHour);
                refresh2();
                break;
        }

        long triggerTime = SystemClock.elapsedRealtime() + anHour ;
        Intent i = new Intent(this, AlarmReceiver.class);
        i.putExtra("friendid", friendid);
        i.putExtra("userid", userid);
        i.putExtra("type", type);
        PendingIntent pi = PendingIntent.getBroadcast(this, type, i, Integer.parseInt(friendid));
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        Log.d("TAG@@@", "跳转@@@@@@@@@@@@@@@@@@@@@@" + triggerTime);
        return super.onStartCommand(intent, flags, startId);
    }

    private void refresh() {
        OkGo.<String>post(Urls.Url_FriendTime).tag(this)
                .params("userid", userid)
                .params("friendid", friendid)
                .params("lookhead", "1")
                .params("sendpix", "0")
                .params("lookimages", "0")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("TAG@@@refresh1-100", "OkGo");
                        Gson gson = new Gson();
                        LoginBean bean = gson.fromJson(response.body(), LoginBean.class);
                        if (bean.getStatus().equals("fail")) {
                            refresh();
                        }

                    }
                });
    }
    private void refresh2() {
        OkGo.<String>post(Urls.Url_FriendTime).tag(this)
                .params("userid", userid)
                .params("friendid", friendid)
                .params("lookhead", "1")
                .params("sendpix", "1")
                .params("lookimages", "0")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("refresh2-110", "OkGo");
                        Gson gson = new Gson();
                        LoginBean bean = gson.fromJson(response.body(), LoginBean.class);
                        if (bean.getStatus().equals("fail")) {
                            refresh2();
                        }

                    }
                });
    }
}
