package com.cfwl.androidapp.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLocationService extends Service {

    private static final String TAG = "LocationService";


    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "start LocationService!");
        netThread.start();
        //初始化定位
        initLocation();
    }

    private void initLocation(){
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
//      //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//      mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
// 		仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，自 v2.9.0 版本支持返回地址描述信息。
// 		Java
// 		设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
 		mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);
 		mLocationOption.setMockEnable(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
    }
    
    
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "StartCommand LocationService!");
        getPosition();
        return super.onStartCommand(intent, flags, startId);

    }

    Handler netHandler = null;

    /**
     * 收发网络数据的线程
     */
    Thread netThread = new Thread(){
        @Override
        public void run() {
            Looper.prepare();
            netHandler = new Handler(){
                public void dispatchMessage(Message msg) {
                    Bundle data = msg.getData();
                    switch(msg.what){
                    case 0x1: //发送位置
                        String macstr = getMac();
                        String longitude = data.getString("longitude");
                        String latitude = data.getString("latitude");
                        String timestr = data.getString("timestr");
                        upDatePosition(macstr,longitude+","+latitude,timestr,timestr);
                        break;

                    }
                };
            };
            Looper.loop();
        }
    };

    public void getPosition(){
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener(){

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
                    if(amapLocation==null){
                        Log.i(TAG, "amapLocation is null!");
                        return;
                    }
                    if(amapLocation.getErrorCode()!=0){
                        Log.i(TAG, "amapLocation has exception errorCode:"+amapLocation.getErrorCode());
                        return;
                    }

                    Double longitude = amapLocation.getLongitude();//获取经度
                    Double latitude = amapLocation.getLatitude();//获取纬度
                    String longitudestr = String.valueOf(longitude);
                    String latitudestr = String.valueOf(latitude);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    String timestr = df.format(date);
                    Log.i(TAG, "longitude,latitude:"+longitude+","+latitude);
                    Log.i(TAG, "time:"+timestr);
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("longitude", longitudestr);
                    data.putString("latitude", latitudestr);
                    data.putString("timestr", timestr);
                    msg.setData(data);
                    msg.what = 0x1;
                    netHandler.sendMessage(msg);
        }

    };



    private String getMac() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }


    int i=0;
    public void upDatePosition(String mac, String position, String recordTime, String reportTime) {
        Log.i(TAG, i+"  "+mac+";"+position+";"+recordTime);
        i++;
    }   

}