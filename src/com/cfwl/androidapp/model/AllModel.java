package com.cfwl.androidapp.model;

import java.util.ArrayList;
import java.util.List;

public class AllModel {
	public static String leftList[]={ "驾驶员信息", "车辆信息",
			"工作录入", "公司简介","高德地图","高德地图2","百度地图","费用管理","选择图片"};
	
	public static int driverCode=0;
	
	public static String driverName="驾驶员A";
	
	public static final String REMOTE_IP = "10.3.46.82";//"localhost";
	
	public static final String URL = "jdbc:mysql://" + REMOTE_IP + "/mydb";
	
	public static final String USER = "root";
	
	public static final String PASSWORD = "";
	
	public static List<String> carCodes=new ArrayList<String>();
	
}
