package com.cfwl.androidapp.activity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.cfwl.androidapp.R;
import com.cfwl.androidapp.adapter.ImageAdapter;
import com.cfwl.androidapp.model.AllModel;
import com.cfwl.androidapp.util.Callback;
import com.cfwl.androidapp.util.Invoker;
import com.cfwl.androidapp.util.JdbcUtils;
import com.cfwl.androidapp.util.Util;
import com.cfwl.androidapp.view.DragLayout;
import com.cfwl.androidapp.view.DragLayout.DragListener;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends Activity {

	private DragLayout dl;
	private GridView gv_img;
	private ImageAdapter adapter;
	private ListView lv;
	private TextView tv_noimg,tv_driverName;
	private ImageView iv_icon, iv_bottom;
	private int code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		SDKInitializer.initialize(getApplicationContext());
		SDKInitializer.initialize(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Util.initImageLoader(this);
		initDragLayout();
		initView();
		
	}

	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl);
//		dl2 = (DragLayout) findViewById(R.id.dl2);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				//滑动到最底部11
				//lv.smoothScrollToPosition(new Random().nextInt(30));
			}

			@Override
			public void onClose() {
				shake();
			}

			@Override
			public void onDrag(float percent) {
				ViewHelper.setAlpha(iv_icon, 1 - percent);
			}
		});
	}
	private void initView() {
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
		gv_img = (GridView) findViewById(R.id.gv_img);
		tv_noimg = (TextView) findViewById(R.id.iv_noimg);
		gv_img.setFastScrollEnabled(true);
		adapter = new ImageAdapter(this);
		tv_driverName = (TextView) findViewById(R.id.tv_driverName);
		gv_img.setAdapter(adapter);
		
		tv_driverName.setText(AllModel.driverName);
		
		gv_img.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						ImageActivity.class);
				intent.putExtra("path", adapter.getItem(position));
				startActivity(intent);
			}
		});
		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item_text, AllModel.leftList));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(AllModel.leftList[position].equals("工作录入")){
					//Util.t(getApplicationContext(), "click " + position);
					//code=position;
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, CarAccountActivity.class);
					startActivity(intent);
					JdbcUtils js=new JdbcUtils();
					try {
						List<Map<String,Object>> back=js.query("insert into 123 values('123','234',5)", null);
						System.out.println(back);
						code=back.size();
						showDialog();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(AllModel.leftList[position].equals("高德地图")){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, AmapActivity.class);
					startActivity(intent);
				}else if(AllModel.leftList[position].equals("高德地图2")){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, Amap.class);
					startActivity(intent);
				}else if(AllModel.leftList[position].equals("费用管理")){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ExpenseActivity.class);
					startActivity(intent);
				}else if(AllModel.leftList[position].equals("选择图片")){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, SelectPicActivity.class);
					startActivity(intent);
				}
			}
		});
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
	}
	

	public void showDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("选择 "+code);
		AlertDialog dialog=builder.create();
		dialog.show();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		loadImage();
	}

	private void loadImage() {
		new Invoker(new Callback() {
			@Override
			public boolean onRun() {
				adapter.addAll(Util.getGalleryPhotos(MainActivity.this));
				return adapter.isEmpty();
			}

			@Override
			public void onBefore() {
				// 转菊花
			}

			@Override
			public void onAfter(boolean b) {
				adapter.notifyDataSetChanged();
				if (b) {
					tv_noimg.setVisibility(View.VISIBLE);
				} else {
					tv_noimg.setVisibility(View.GONE);
					String s = "file://" + adapter.getItem(0);
//					ImageLoader.getInstance().displayImage(s, iv_icon);
					//ImageLoader.getInstance().displayImage(s, iv_bottom);
				}
				shake();
			}
		}).start();

	}

	private void shake() {
		iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
	}

}
