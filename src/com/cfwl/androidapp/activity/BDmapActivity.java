package com.cfwl.androidapp.activity;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.cfwl.androidapp.R;
import com.cfwl.androidapp.listener.MyLocationListener;

import android.app.Activity;
import android.os.Bundle;

public class BDmapActivity extends Activity {  
	TextureMapView mMapView = null;  
	public static BaiduMap bdmap=null;
	public LocationClient mLocationClient = null;
	public boolean isFirstLocation = true;
	public BDLocationListener myListener = new MyLocationListener();
	public LatLng mLastLocationData;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
    	SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.activity_bdmap);  
        //获取地图控件引用  
        mMapView = (TextureMapView) findViewById(R.id.bmapView);  
        bdmap=mMapView.getMap();
        bdmap.setMyLocationEnabled(true);
        initLocation();
        mLocationClient.registerLocationListener( myListener ); 
        mLocationClient.start();
    }  
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
    private void initLocation(){
    	mLocationClient = new LocationClient(getApplicationContext());     
//    	mLocationClient = new LocationClient(this); 
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
     
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
     
        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
     
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
     
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
     
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
     
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
     
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
     
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死  
     
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
     
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
     
        mLocationClient.setLocOption(option);
        
        
        
    }
    }