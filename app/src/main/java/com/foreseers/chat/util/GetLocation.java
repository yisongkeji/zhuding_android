package com.foreseers.chat.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import com.foreseers.chat.bean.LocationBean;

import static android.content.Context.LOCATION_SERVICE;
import static com.foreseers.chat.global.MyApplication.getContext;

public class GetLocation {
    private LocationManager locationManager;// 位置服务
    private Location location;// 位置
    private LocationBean locationBean = new LocationBean();

    public  LocationBean getLocation(Activity activity) {

        try {
            // 获取系统服务
            locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
            // 判断GPS是否可以获取地理位置，如果可以，展示位置，
            // 如果GPS不行，再判断network，还是获取不到，那就报错
            if (locationInitByGPS() || locationInitByNETWORK()) {
                // 上面两个只是获取经纬度的，获取经纬度location后，再去调用谷歌解析来获取地理位置名称
                Log.i("GPS", "locationInit: 经度" + location.getLatitude() + "维度" + location.getLongitude());
                showLocation(location);
            } else {
                Log.e("Exception", "获取地理位置失败 ");
            }
        } catch (Exception e) {
            Log.e("Exception", "getLocation: " + e.toString());
        }
        return locationBean;
    }

    //这是根据经纬度，向谷歌的API解析发网络请求，然后获取response，这里超时时间不要太短，否则来不及返回位置信息，直接失败了
    private void showLocation(final Location loc) {
        List<Address> addList = null;
        Geocoder ge = new Geocoder(getContext());
        try {
            addList = ge.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            addList = ge.getFromLocation(22.3043710000, 114.1853080000, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address ad = addList.get(i);

                //国
                locationBean.setCountry(ad.getCountryName());
                //省
                locationBean.setCity(ad.getAdminArea());
                //市
                locationBean.setArea(ad.getLocality());
                //区
                locationBean.setAddr(ad.getSubLocality());
                //街
                locationBean.setAddrs(ad.getFeatureName());

                //经度
                locationBean.setLat(location.getLatitude());
                //维度
                locationBean.setLng(location.getLongitude());
            }
        }else {

            //经度
            locationBean.setLat(location.getLatitude());
            //维度
            locationBean.setLng(location.getLongitude());

        }


    }


    // GPS去获取location经纬度
    @SuppressLint("MissingPermission")
    public boolean locationInitByGPS() {
        // 没有GPS，直接返回
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 0, locationListener);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            return true;//设置location成功，返回true
        } else {
            return false;
        }
    }

    // network去获取location经纬度
    @SuppressLint("MissingPermission")
    public boolean locationInitByNETWORK() {
        // 没有NETWORK，直接返回
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            return true;
        } else {
            return false;
        }
    }


    //监听地理位置变化，地理位置变化时，能够重置location
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
//            tv_show.setText("更新失败失败");
        }

        @Override
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                location = loc;
                showLocation(location);
            }
        }
    };

}
