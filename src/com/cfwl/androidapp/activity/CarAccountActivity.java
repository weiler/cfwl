package com.cfwl.androidapp.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cfwl.androidapp.R;
import com.cfwl.androidapp.model.AllModel;
import com.cfwl.androidapp.network.HttpThread;
import com.cfwl.androidapp.util.Util;
import com.cfwl.androidapp.view.DragLayout;
import com.cfwl.androidapp.view.MyDataPick;
import com.cfwl.androidapp.view.DragLayout.DragListener;
import com.nineoldandroids.view.ViewHelper;

import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;

public class CarAccountActivity extends Activity {

	private DragLayout dl;
	private ListView lv;
	private ImageView iv_icon;
	private EditText et_selectDate, et_selectTime, et_selectDate_end, et_selectTime_end;
	private static int top;
	private Spinner sp_carCode, sp_driverName;
	// 是否支付
	private CheckBox cb_pay;
	private EditText et_payMoney, et_payPerson, et_totalHours, et_workPlace, et_workUnit, et_sitePerson;
	private TextView tv_payMoney, tv_payPerson;
	private Button bu_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_account);
		Util.initImageLoader(this);
		initDragLayout();
		initView();
		initDateCheak();
		initsp_carCode();
		initPay();
		initSave();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl2);
		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				lv.smoothScrollToPosition(new Random().nextInt(30));
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
		iv_icon = (ImageView) findViewById(R.id.iv_icon2);
		lv = (ListView) findViewById(R.id.lv2);
		lv.setAdapter(new ArrayAdapter<String>(CarAccountActivity.this, R.layout.item_text, AllModel.leftList));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Util.t(getApplicationContext(), "click " + position);
				Intent intent = new Intent();
				intent.setClass(CarAccountActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
	}

	protected void initSave() {
		bu_save = (Button) findViewById(R.id.bu_save);
		et_workPlace = (EditText) findViewById(R.id.et_workPlace);
		et_workUnit = (EditText) findViewById(R.id.et_workUnit);
		et_sitePerson = (EditText) findViewById(R.id.et_sitePerson);
		bu_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HttpThread ht = new HttpThread();
				ht.carAccount.setCar_code(sp_carCode.getSelectedItem().toString());
				ht.carAccount.setDriver(sp_driverName.getSelectedItem().toString());
				ht.carAccount
						.setBegin_time(et_selectDate.getText().toString() + " " + et_selectTime.getText().toString());
				ht.carAccount.setEnd_time(
						et_selectDate_end.getText().toString() + " " + et_selectTime_end.getText().toString());
				ht.carAccount.setPayed(cb_pay.isChecked() ? "1" : "0");
				ht.carAccount.setPay_person(et_payPerson.getText().toString());
				ht.carAccount.setTotal_hours(Double.valueOf(et_totalHours.getText().toString()));
				ht.carAccount.setCompany(et_workUnit.getText().toString());
				ht.carAccount.setWork_place(et_workPlace.getText().toString());

				List<NameValuePair> postParam = new ArrayList<NameValuePair>();
				postParam.add(new BasicNameValuePair("car_code", sp_carCode.getSelectedItem().toString()));
				postParam.add(new BasicNameValuePair("driver", sp_driverName.getSelectedItem().toString()));
				postParam.add(new BasicNameValuePair("begin_time",
						et_selectDate.getText().toString() + " " + et_selectTime.getText().toString()));
				postParam.add(new BasicNameValuePair("end_time",
						et_selectDate_end.getText().toString() + " " + et_selectTime_end.getText().toString()));
				postParam.add(new BasicNameValuePair("payed", cb_pay.isChecked() ? "1" : "0"));
				postParam.add(new BasicNameValuePair("pay_person", et_payPerson.getText().toString()));
				postParam.add(new BasicNameValuePair("total_hours", et_totalHours.getText().toString()));
				postParam.add(new BasicNameValuePair("company", et_workUnit.getText().toString()));
				postParam.add(new BasicNameValuePair("work_place", et_workPlace.getText().toString()));
				postParam.add(new BasicNameValuePair("note", et_workPlace.getText().toString()));
				ht.postParam = postParam;
				ht.start();
			}
		});
	}

	// 加载支付信息
	protected void initPay() {
		et_payMoney = (EditText) findViewById(R.id.et_payMoney);
		et_payPerson = (EditText) findViewById(R.id.et_payPerson);
		tv_payMoney = (TextView) findViewById(R.id.tv_payMoney);
		tv_payPerson = (TextView) findViewById(R.id.tv_payPerson);

		et_payMoney.setVisibility(View.INVISIBLE);
		et_payPerson.setVisibility(View.INVISIBLE);
		tv_payMoney.setVisibility(View.INVISIBLE);
		tv_payPerson.setVisibility(View.INVISIBLE);

		cb_pay = (CheckBox) findViewById(R.id.cb_pay);
		cb_pay.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cb_pay.setText("已付款");
					et_payMoney.setVisibility(View.VISIBLE);
					et_payPerson.setVisibility(View.VISIBLE);
					tv_payMoney.setVisibility(View.VISIBLE);
					tv_payPerson.setVisibility(View.VISIBLE);
				} else {
					cb_pay.setText("未付款");
					et_payMoney.setVisibility(View.INVISIBLE);
					et_payPerson.setVisibility(View.INVISIBLE);
					tv_payMoney.setVisibility(View.INVISIBLE);
					tv_payPerson.setVisibility(View.INVISIBLE);
				}
			}
		});

	}

	protected void initsp_carCode() {
		ArrayList<String> carCodes = new ArrayList<String>();
		carCodes.add("川F09888");
		carCodes.add("川F18888");
		carCodes.add("川F22888");
		carCodes.add("川F22999");
		carCodes.add("川F19888");

		ArrayAdapter<String> carCodeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				carCodes);
		carCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp_carCode = (Spinner) findViewById(R.id.sp_carCode);
		sp_carCode.setAdapter(carCodeAdapter);

		sp_carCode.setOnItemSelectedListener(new OnItemSelectedListener() {
			// parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				findViewById(R.id.tv_carCode);
				parent.getAdapter();
			}

			// 没有选中时的处理
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		ArrayList<String> driverNames = new ArrayList<String>();
		driverNames.add("魏老总");
		driverNames.add("刘老师");
		driverNames.add("伙食团张");
		driverNames.add("驾驶员A");
		driverNames.add("驾驶员B");

		ArrayAdapter<String> driverNameAdapter = new ArrayAdapter<String>(CarAccountActivity.this,
				android.R.layout.simple_spinner_item, driverNames);
		driverNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp_driverName = (Spinner) findViewById(R.id.sp_driverName);
		sp_driverName.setAdapter(driverNameAdapter);

		sp_driverName.setOnItemSelectedListener(new OnItemSelectedListener() {
			// parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				parent.getAdapter();
			}

			// 没有选中时的处理
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	protected void initDateCheak() {
		// 开始工作时间
		et_totalHours = (EditText) findViewById(R.id.et_totalHours);
		Calendar nowDateTime = Calendar.getInstance();
		et_selectDate = (EditText) findViewById(R.id.et_selectDate);

		et_selectDate.setOnTouchListener(dateTouch);
		et_selectDate.setOnFocusChangeListener(dateFouseChage);

		et_selectTime = (EditText) findViewById(R.id.et_selectTime);
		et_selectTime.setOnTouchListener(timeTouch);
		et_selectTime.setOnFocusChangeListener(timeFouseChage);
		// 设置默认时间为当前时间
		et_selectDate.setText(nowDateTime.get(Calendar.YEAR) + "-" + nowDateTime.get(Calendar.MONTH) + "-"
				+ nowDateTime.get(Calendar.DAY_OF_MONTH));
		et_selectTime.setText(nowDateTime.get(Calendar.HOUR_OF_DAY) + ":" + nowDateTime.get(Calendar.MINUTE));

		// 工作完成时间：
		et_selectDate_end = (EditText) findViewById(R.id.et_selectDate_end);

		et_selectDate_end.setOnTouchListener(dateTouchEnd);
		et_selectDate_end.setOnFocusChangeListener(dateFouseChageEnd);

		et_selectTime_end = (EditText) findViewById(R.id.et_selectTime_end);
		et_selectTime_end.setOnTouchListener(timeTouchEnd);
		et_selectTime_end.setOnFocusChangeListener(timeFouseChageEnd);
		// 设置默认时间为当前时间
		et_selectDate_end.setText(nowDateTime.get(Calendar.YEAR) + "-" + nowDateTime.get(Calendar.MONTH) + "-"
				+ nowDateTime.get(Calendar.DAY_OF_MONTH));
		et_selectTime_end.setText(nowDateTime.get(Calendar.HOUR_OF_DAY) + ":" + nowDateTime.get(Calendar.MINUTE));

		et_selectTime.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String end = et_selectTime_end.getText().toString();
				String begin = et_selectTime.getText().toString();
				if (begin != null && end != null) {
					String dd = String.valueOf(Double.parseDouble(end.split(":")[0])
							- Double.parseDouble(begin.split(":")[0])
							+ Double.parseDouble(String.format("%.2f", (Double.parseDouble(end.split(":")[1]) - Double.parseDouble(begin.split(":")[1])) / 60)));
					et_totalHours.setText(dd);
				}
			}
		});
		et_selectTime_end.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String end = et_selectTime_end.getText().toString();
				String begin = et_selectTime.getText().toString();
				if (begin != null && end != null) {
					String dd = String.valueOf(Double.parseDouble(end.split(":")[0])
							- Double.parseDouble(begin.split(":")[0])
							+ Double.parseDouble(String.format("%.2f", (Double.parseDouble(end.split(":")[1]) - Double.parseDouble(begin.split(":")[1])) / 60)));
					et_totalHours.setText(dd);
				}
			}
		});

	}

	OnTouchListener dateTouch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				showDatePickDlg(v.getTop());
				return true;
			}
			return false;
		}
	};
	OnFocusChangeListener dateFouseChage = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				showDatePickDlg(v.getTop());
			}
		}
	};

	OnTouchListener timeTouch = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				showTimePickDlg(v.getTop());
				return true;
			}
			return false;
		}
	};
	OnFocusChangeListener timeFouseChage = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				showTimePickDlg(v.getTop());
			}
		}
	};

	OnTouchListener dateTouchEnd = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				showDatePickDlgEnd(v.getTop());
				return true;
			}
			return false;
		}
	};
	OnFocusChangeListener dateFouseChageEnd = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				showDatePickDlgEnd(v.getTop());
			}
		}
	};

	OnTouchListener timeTouchEnd = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				showTimePickDlgEnd(v.getTop());
				return true;
			}
			return false;
		}
	};
	OnFocusChangeListener timeFouseChageEnd = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				showTimePickDlgEnd(v.getTop());
			}
		}
	};

	/// bug点： 用top值来区分 begin时间和end时间
	protected void showTimePickDlg(int top) {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog datePickerDialog = new TimePickerDialog(CarAccountActivity.this,
				calendar.get(Calendar.HOUR_OF_DAY), new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						CarAccountActivity.this.et_selectTime.setText(hourOfDay + ":" + minute);
					}
				}, calendar.get(Calendar.MINUTE), calendar.get(Calendar.HOUR_OF_DAY), true);
		datePickerDialog.show();

	}

	protected void showDatePickDlg(int top) {
		Calendar calendar = Calendar.getInstance();
		MyDataPick datePickerDialog = new MyDataPick(CarAccountActivity.this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				CarAccountActivity.this.et_selectDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();
	}

	protected void showTimePickDlgEnd(int top) {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog datePickerDialog = new TimePickerDialog(CarAccountActivity.this,
				calendar.get(Calendar.HOUR_OF_DAY), new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						CarAccountActivity.this.et_selectTime_end.setText(hourOfDay + ":" + minute);
					}
				}, calendar.get(Calendar.MINUTE), calendar.get(Calendar.HOUR_OF_DAY), true);
		datePickerDialog.show();

	}

	protected void showDatePickDlgEnd(int top) {
		Calendar calendar = Calendar.getInstance();
		MyDataPick datePickerDialog = new MyDataPick(CarAccountActivity.this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				CarAccountActivity.this.et_selectDate_end.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void shake() {
		iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
	}

	public void showDialogs(String a) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(a);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
