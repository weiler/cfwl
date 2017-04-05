package com.cfwl.androidapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSON;
import com.amap.api.services.route.RouteSearch.FromAndTo;
import com.cfwl.androidapp.model.AllModel;
import com.cfwl.androidapp.model.CarAccount;

import android.util.Log;

public class HttpThread extends Thread {
	public CarAccount carAccount;
	public List <NameValuePair> postParam;
	@Override
	public void run() {
		httpPost();
	}
	
	public HttpThread() {
		carAccount=new CarAccount();
		postParam=new ArrayList<NameValuePair>();
	}
	
	private void httpGet(){
      //用HttpClient发送请求，分为五步
      //第一步：创建HttpClient对象
      HttpClient httpClient = new DefaultHttpClient();
//      HttpRequest.this
      String name="android";
      String pwd="123456";
      //注意，下面这一行中，我之前把链接中的"test"误写成了"text"，导致调BUG调了半天没弄出来，真是浪费时间啊
      // String url = "http://192.168.1.112:8080/test.jsp?name=" + name+ "&password=" + pwd;
      String url = "http://10.3.45.109:8099/rp/CfMain/lg?name=" + name+ "&password=" + pwd;
      //     String url = "http://www.baidu.com";
      //第二步：创建代表请求的对象,参数是访问的服务器地址
      HttpGet httpGet = new HttpGet(url);
      try {
          //第三步：执行请求，获取服务器发还的相应对象
          HttpResponse response = httpClient.execute(httpGet);
          //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
          if (response.getStatusLine().getStatusCode() == 200) {
              //第五步：从相应对象当中取出数据，放到entity当中
              HttpEntity entity = response.getEntity();
              BufferedReader reader = new BufferedReader(
                      new InputStreamReader(entity.getContent()));
              String result = reader.readLine();
              Log.d("HTTP", "GET:" + result);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
//
	}
	
	private void httpPost(){
//		String jsCarAccount = JSON.toJSONString(carAccount);
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://10.3.45.109:8099/rp/cf_car_account/add");
//		EditText et=v.findViewById(R.id.et_selectDate);
		    //拼接参数
//		    List <NameValuePair> postParam = new ArrayList <NameValuePair>();
//		    postParam.add(new BasicNameValuePair("carAccount", jsCarAccount));
		    try {
				httpPost.setEntity(new UrlEncodedFormEntity(postParam,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    try {
				HttpResponse response = httpClient.execute(httpPost);
				Log.i("response",response.toString());
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	void okHttpPost(){
		OkHttpClient client = new OkHttpClient();
		FormBody.Builder formBodyBuild = new FormBody.Builder();
		formBodyBuild.add("s123", "ssfsf");
		Request request = new Request.Builder().url(AllModel.URL)
				.post(formBodyBuild.build()).build();
		Call call = client.newCall(request);
		call.enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
