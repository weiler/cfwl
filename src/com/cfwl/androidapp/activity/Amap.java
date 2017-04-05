package com.cfwl.androidapp.activity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.baidu.location.LocationClient;
import com.cfwl.androidapp.R;
import com.cfwl.androidapp.listener.MyLocationListener;
import com.cfwl.androidapp.model.AllModel;
import com.cfwl.androidapp.model.myLocationModel;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class Amap extends Activity implements LocationSource, AMapLocationListener {
	  MapView mMapView = null;
//	  map
	  AMap aMap = null;
	  OnLocationChangedListener mListener;
	  AMapLocationClient mlocationClient;
	  
	  AMapLocationClientOption mLocationOption;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.activity_amap2);
	    //获取地图控件引用
	    mMapView = (MapView) findViewById(R.id.map);
	    //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
	    mMapView.onCreate(savedInstanceState);
	    if (aMap == null) {
	        aMap = mMapView.getMap();        
	    }
	    initLocation();
	  }
	  
	  void initLocation(){
		  	MyLocationStyle myLocationStyle;
	        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
	        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
	        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
	        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
	        //MyLocationStyle myLocationIcon(BitmapDescriptor myLocationIcons);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
	        myLocationStyle.strokeColor(new Color().BLUE);//设置定位蓝点精度圆圈的边框颜色的方法。
	        myLocationStyle.radiusFillColor(new Color().RED);//设置定位蓝点精度圆圈的填充颜色的方法。
	        myLocationStyle.strokeWidth(12);
	        
	        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
	        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
	        aMap.setLocationSource(this);
	        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
	  }
	  
	  @Override
	  protected void onDestroy() {
	    //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
	    super.onDestroy();
	    mMapView.onDestroy();
	     if(null != mlocationClient){
	         mlocationClient.onDestroy();
	     }
	  }
	 @Override
	 protected void onResume() {
	    super.onResume();
	    //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
	    mMapView.onResume();
	    }
	 @Override
	 protected void onPause() {
	    super.onPause();
	    //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
	    mMapView.onPause();
	    }
	 @Override
	 protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
	    mMapView.onSaveInstanceState(outState);
	  }
	 
	 /**
	  * 激活定位
	  */
	 @Override
	 public void activate(OnLocationChangedListener listener) {
	     mListener = listener;
	     if (mlocationClient == null) {
	         //初始化定位
	         mlocationClient = new AMapLocationClient(this);
	         //初始化定位参数
	         mLocationOption = new AMapLocationClientOption();
	         mLocationOption.setMockEnable(true);
	         //设置定位回调监听
	         mlocationClient.setLocationListener(this);
//	         //设置为高精度定位模式
//	         mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
//	 		仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，自 v2.9.0 版本支持返回地址描述信息。
//	 		Java
//	 		设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
	 		mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);
	         //设置定位参数
	         mlocationClient.setLocationOption(mLocationOption);
	         // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
	         // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
	         // 在定位结束后，在合适的生命周期调用onDestroy()方法
	         // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
	        
	         mlocationClient.startLocation();//启动定位
	     }
	 }
	 
	 /**
	  * 停止定位
	  */
	 @Override
	 public void deactivate() {
	     mListener = null;
	     if (mlocationClient != null) {
	         mlocationClient.stopLocation();
	         mlocationClient.onDestroy();
	     }
	     mlocationClient = null;
	 }

	 /**
	  * 定位成功后回调函数
	  */
	 @Override
	 public void onLocationChanged(AMapLocation amapLocation) {
		 Log.i("amapLocation", amapLocation.getCountry());
	     if (mListener != null&&amapLocation != null) {
	         if (amapLocation != null
	                 &&amapLocation.getErrorCode() == 0) {
	        	 Log.i("toStr", amapLocation.toStr(1));
	        	 Log.i("getAddress", amapLocation.getAddress());
//	        	 Log.i("toString", amapLocation.toString());
	             mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
	         } else {
	             String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
	             Log.e("AmapErr",errText);
	         }
	     }
//	     SetBefor
	     myLocationModel myLocation=new myLocationModel();
	     myLocation.setAdCode(amapLocation.getAdCode());
	     myLocation.setAdCode(amapLocation.getAdCode());
	     myLocation.setAddress(amapLocation.getAddress());
	     myLocation.setAltitude(amapLocation.getAltitude());
	     myLocation.setAoiName(amapLocation.getAoiName());
	     myLocation.setBearing(amapLocation.getBearing());
	     myLocation.setCity(amapLocation.getCity());
	     myLocation.setCityCode(amapLocation.getCityCode());
	     myLocation.setCountry(amapLocation.getCountry());
	     myLocation.setDistrict(amapLocation.getDistrict());
	     myLocation.setErrorCode(amapLocation.getErrorCode());
	     myLocation.setErrorInfo(amapLocation.getErrorInfo());
	     myLocation.setLatitude(amapLocation.getLatitude());
	     myLocation.setLocationDetail(amapLocation.getLocationDetail());
	     myLocation.setLocationType(amapLocation.getLocationType());
	     myLocation.setLongitude(amapLocation.getLongitude());
	     myLocation.setProvince(amapLocation.getProvince());
	     myLocation.setSpeed(amapLocation.getSpeed());
	     myLocation.setStreet(amapLocation.getStreet());
	     myLocation.setStreetNum(amapLocation.getStreetNum());
	     myLocation.setToStr(amapLocation.toStr());
	     myLocation.setDriver(AllModel.driverName);
	     myLocation.setDriver_id(AllModel.driverCode);
//	     myLocation.seti
//	     json
	     addMarkerToMap();
	 }
	    
	 
	 
	 //根据经度纬度在地图上增加一个点
	 private void addMarkerToMap() {
	        LatLng latLng = new LatLng(38, 114.5);
	        MarkerOptions markerOption = new MarkerOptions();
	        markerOption.position(latLng);
	        markerOption.draggable(true);
	        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.crane));
	        markerOption.title("吊车详情").snippet("成都市中海诚丰物流吊车A");
	        Marker marker = aMap.addMarker(markerOption);
//	        marker.setRotateAngle(30);
	        marker.showInfoWindow();

	    }
	}
