package com.cfwl.androidapp.model;

import java.io.Serializable;

public class myLocationModel extends TopModel implements Serializable{

	private static final long serialVersionUID = 1L;
	public int driver_id;//驾驶员id
	public String driver;//驾驶员名称
	public String adCode;//获取区域编码
	public String address;//获取地址
	public double altitude;//获取海拔高度 单位：米
	public String aoiName;//获取兴趣面名称
	public float bearing;//获取方向角 单位：度
	public String city;//获取城市名称
	public String cityCode;//获取城市编码
	public String country;//获取国家名称
	public String district;//获取区的名称
	public int errorCode;//获取错误码
	public String errorInfo;//获取错误信息
	public double latitude;//获取纬度
	public String locationDetail;//获取定位信息描述
	public int locationType;//获取定位结果来源
	public double longitude;//获取经度
	public String province;//获取省的名称
	public float speed;//获取当前速度 单位：米/秒
	public String street;//获取街道名称
	public String streetNum;//获取门牌号
	public String toStr;//将定位结果转换成字符串
	

	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getAdCode() {
		return adCode;
	}
	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public String getAoiName() {
		return aoiName;
	}
	public void setAoiName(String aoiName) {
		this.aoiName = aoiName;
	}
	public float getBearing() {
		return bearing;
	}
	public void setBearing(float bearing) {
		this.bearing = bearing;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getLocationDetail() {
		return locationDetail;
	}
	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}
	public int getLocationType() {
		return locationType;
	}
	public void setLocationType(int locationType) {
		this.locationType = locationType;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetNum() {
		return streetNum;
	}
	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}
	public String getToStr() {
		return toStr;
	}
	public void setToStr(String toStr) {
		this.toStr = toStr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}
