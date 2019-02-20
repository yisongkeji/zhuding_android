package com.foreseers.chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.foreseers.chat.bean.LoginBean;
import com.foreseers.chat.util.Urls;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class AlbumTimeService extends Service {

    private String userid;
    private String friendid;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userid = intent.getStringExtra("userid");
        friendid = intent.getStringExtra("friendid");

        refresh();


        return super.onStartCommand(intent, flags, startId);
    }

    private void refresh( ) {
        OkGo.<String>post(Urls.Url_FriendTime).tag(this)
                .params("userid",userid)
                .params("friendid",friendid)
                .params("lookhead","1")
                .params("sendpix","1")
                .params("lookimages","1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("TAG@@@", "OkGo");
                        Gson gson=new Gson();
                        LoginBean bean=gson.fromJson(response.body(),LoginBean.class);
                        if (bean.getStatus().equals("fail")){
                            refresh();
                        }

                    }
                });
    }
}
