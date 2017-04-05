package com.cfwl.androidapp.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import android.util.Log;

public class MyAMapLocationListener implements AMapLocationListener {

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (amapLocation != null) {
		    if (amapLocation.getErrorCode() == 0) {
		    	Log.i("getLocationType", ""+amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
		    	Log.i("getLatitude", ""+amapLocation.getLatitude());//获取纬度
		    	Log.i("getLongitude", ""+amapLocation.getLongitude());//获取经度
		    	Log.i("cg", ""+"chenggongggggggggggggggggggggggggggggggggggggggggggg");//获取经度
		    	amapLocation.getAccuracy();//获取精度信息
		    	amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
		    	amapLocation.getCountry();//国家信息
		    	amapLocation.getProvince();//省信息
		    	amapLocation.getCity();//城市信息
		    	amapLocation.getDistrict();//城区信息
		    	amapLocation.getStreet();//街道信息
		    	amapLocation.getStreetNum();//街道门牌号信息
		    	amapLocation.getCityCode();//城市编码
		    	amapLocation.getAdCode();//地区编码
		    	amapLocation.getAoiName();//获取当前定位点的AOI信息
		    	amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
		    	amapLocation.getFloor();//获取当前室内定位的楼层
		    	//获取定位时间
		    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	Date date = new Date(amapLocation.getTime());
		    	df.format(date);
		//可在其中解析amapLocation获取相应内容。
		    }else {
		    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
		    Log.e("AmapError","location Error, ErrCode:"
		        + amapLocation.getErrorCode() + ", errInfo:"
		        + amapLocation.getErrorInfo());
		    }
//		    this.onLocationChanged(amapLocation);
		}
	}

}
