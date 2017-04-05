//package com.cfwl.androidapp.activity;
//
//import com.amap.api.maps2d.MapView;
//import com.cfwl.androidapp.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//
//public class AmapActivity extends Activity {
//  MapView mMapView = null;
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState); 
//    setContentView(R.layout.activity_main);
//    //获取地图控件引用
//    mMapView = (MapView) findViewById(R.id.map);
//    //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
//    mMapView.onCreate(savedInstanceState);
//  }
//  @Override
//  protected void onDestroy() {
//    super.onDestroy();
//    //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//    mMapView.onDestroy();
//  }
// @Override
// protected void onResume() {
//    super.onResume();
//    //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//    mMapView.onResume();
//    }
// @Override
// protected void onPause() {
//    super.onPause();
//    //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//    mMapView.onPause();
//    }
// @Override
// protected void onSaveInstanceState(Bundle outState) {
//    super.onSaveInstanceState(outState);
//    //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//    mMapView.onSaveInstanceState(outState);
//  } 
//}
package com.cfwl.androidapp.activity;
 

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerBase;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.cfwl.androidapp.R;
import com.cfwl.androidapp.listener.MyAMapLocationListener;
 

/**
 * AMapV1地图中介绍如何显示世界图
 */
public class AmapActivity extends Activity {

	private MapView mapView;
	private AMap aMap;
	private Button basicmap;
	private Button rsmap;
	//声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	//监听器
	private MyAMapLocationListener myAMapLocationLinstener=new MyAMapLocationListener();
	//声明AMapLocationClientOption对象
	public AMapLocationClientOption mLocationOption = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amap);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		aMap=mapView.getMap();

		aMap.setMyLocationEnabled(true);
		//初始化定位
		mLocationClient = new AMapLocationClient(getApplicationContext());
//		init();
		// 设置定位监听
		aMap.setMyLocationEnabled(true);
		mLocationClient.setLocationListener(myAMapLocationLinstener);
		
		MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
//        myLocationStyle.myLocationType();//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        //myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
		
		
		//启动定位
		initAMap();
		initLocation();
		mLocationClient.startLocation();
		Log.i("cg1", ""+"chenggongggggggggggggggggggggggggggggggggggggggggggg");//获取经度
	}

	//初始化定位
	private void initLocation() {
		//初始化AMapLocationClientOption对象
		mLocationOption = new AMapLocationClientOption();
		mLocationOption.setMockEnable(true);
//		mLocationOption.
//		高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
//		Java
//		设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);

//		低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
//		Java
//		设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//		mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);

//		仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，自 v2.9.0 版本支持返回地址描述信息。
//		Java
//		设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
		mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);
		
		//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
		mLocationOption.setInterval(1000);

		//设置是否返回地址信息（默认返回地址信息）
		mLocationOption.setNeedAddress(true);

		//给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);

	}
	/**
	 * 初始化AMap对象
	 */
    private void initAMap() {  
        if (aMap == null) {  
            aMap = mapView.getMap();  
        }  
  
//        setUpMap();  
  
    }  
  
//    private void setUpMap() {  
//        //初始化定位参数  
//        mLocationOption = new AMapLocationClientOption();  
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式  
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);  
//        //设置是否返回地址信息（默认返回地址信息）  
//        mLocationOption.setNeedAddress(true);  
//        //设置是否只定位一次,默认为false  
//        mLocationOption.setOnceLocation(false);  
//        //设置是否强制刷新WIFI，默认为强制刷新  
//        mLocationOption.setWifiActiveScan(true);  
//        //设置是否允许模拟位置,默认为false，不允许模拟位置  
//        mLocationOption.setMockEnable(false);  
//        //设置定位间隔,单位毫秒,默认为2000ms  
//        mLocationOption.setInterval(2000);  
//        //给定位客户端对象设置定位参数  
//        mLocationClient.setLocationOption(mLocationOption);  
//        //启动定位  
//        mLocationClient.startLocation();  
//    }  
//	private void init() {
//		if (aMap == null) {
//			aMap = mapView.getMap();
//			
//		}
//		basicmap = (Button)findViewById(R.id.basicmap);
//		basicmap.setOnClickListener(this);
//		rsmap = (Button)findViewById(R.id.rsmap);
//		rsmap.setOnClickListener(this);
//
//		mRadioGroup = (RadioGroup) findViewById(R.id.check_language);
//
//		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				if (checkedId == R.id.radio_en) {
//					aMap.setMapLanguage(AMap.ENGLISH);
//				} else {
//					aMap.setMapLanguage(AMap.CHINESE);
//				}
//			}
//		});
//	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

//	@Override
//	public void onLocationChanged(AMapLocation arg0) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void onClick(View v) {
////		switch (v.getId()) {
////		case R.id.basicmap:
////			aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
////			break;
////		case R.id.rsmap:
////			aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
////			break;
////		}
////		
//	}

}
