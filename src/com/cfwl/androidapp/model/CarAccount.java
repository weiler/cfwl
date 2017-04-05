package com.cfwl.androidapp.model;
import java.io.Serializable;
public class CarAccount extends TopModel implements Serializable {
	/** serialVersionUID*/
	public static final long serialVersionUID = 1L;
	public Long id;//	编号
	public String work_date;//	工作时间
	public String company;//	作业单位
	public String work_place;//	作业地点
	//et_workPlace,et_sitePerson
	public String car_code;//	车牌号
	public String driver;//	驾驶员
	public String begin_time;//	开始时间
	public String end_time;//	结束时间
	public Double total_hours;//	时长
	public Double hour_price;//	单价（每小时价格）
	public Double total_price	;//总价（时长*单价）
	public String payed;//	是否支付
	public String pay_person;//	支付人
	public String pay_time;//	支付时间
	public String note;//	备注;//
	
	public Long getid() {
		return id;
	}
	public void setid(Long id) {
		this.id = id;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getWork_place() {
		return work_place;
	}
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}
	public String getCar_code() {
		return car_code;
	}
	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Double getTotal_hours() {
		return total_hours;
	}
	public void setTotal_hours(Double total_hours) {
		this.total_hours = total_hours;
	}
	public Double getHour_price() {
		return hour_price;
	}
	public void setHour_price(Double hour_price) {
		this.hour_price = hour_price;
	}
	public Double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}
	public String getPayed() {
		return payed;
	}
	public void setPayed(String payed) {
		this.payed = payed;
	}
	public String getPay_person() {
		return pay_person;
	}
	public void setPay_person(String pay_person) {
		this.pay_person = pay_person;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}