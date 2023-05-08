package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.service.BuildingInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class RoomInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明所在宿舍下拉框
	private Spinner spinner_buildingObj;
	private ArrayAdapter<String> buildingObj_adapter;
	private static  String[] buildingObj_ShowText  = null;
	private List<BuildingInfo> buildingInfoList = null;
	/*所在宿舍管理业务逻辑层*/
	private BuildingInfoService buildingInfoService = new BuildingInfoService();
	// 声明房间名称输入框
	private EditText ET_roomName;
	// 声明房间类型输入框
	private EditText ET_roomTypeName;
	// 声明房间价格(元/月)输入框
	private EditText ET_roomPrice;
	// 声明总床位输入框
	private EditText ET_totalBedNumber;
	// 声明剩余床位输入框
	private EditText ET_leftBedNum;
	// 声明寝室电话输入框
	private EditText ET_roomTelephone;
	// 声明附加信息输入框
	private EditText ET_roomMemo;
	protected String carmera_path;
	/*要保存的房间信息信息*/
	RoomInfo roomInfo = new RoomInfo();
	/*房间信息管理业务逻辑层*/
	private RoomInfoService roomInfoService = new RoomInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.roominfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加房间信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_buildingObj = (Spinner) findViewById(R.id.Spinner_buildingObj);
		// 获取所有的所在宿舍
		try {
			buildingInfoList = buildingInfoService.QueryBuildingInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int buildingInfoCount = buildingInfoList.size();
		buildingObj_ShowText = new String[buildingInfoCount];
		for(int i=0;i<buildingInfoCount;i++) { 
			buildingObj_ShowText[i] = buildingInfoList.get(i).getBuildingName();
		}
		// 将可选内容与ArrayAdapter连接起来
		buildingObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, buildingObj_ShowText);
		// 设置下拉列表的风格
		buildingObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_buildingObj.setAdapter(buildingObj_adapter);
		// 添加事件Spinner事件监听
		spinner_buildingObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				roomInfo.setBuildingObj(buildingInfoList.get(arg2).getBuildingId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_buildingObj.setVisibility(View.VISIBLE);
		ET_roomName = (EditText) findViewById(R.id.ET_roomName);
		ET_roomTypeName = (EditText) findViewById(R.id.ET_roomTypeName);
		ET_roomPrice = (EditText) findViewById(R.id.ET_roomPrice);
		ET_totalBedNumber = (EditText) findViewById(R.id.ET_totalBedNumber);
		ET_leftBedNum = (EditText) findViewById(R.id.ET_leftBedNum);
		ET_roomTelephone = (EditText) findViewById(R.id.ET_roomTelephone);
		ET_roomMemo = (EditText) findViewById(R.id.ET_roomMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加房间信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取房间名称*/ 
					if(ET_roomName.getText().toString().equals("")) {
						Toast.makeText(RoomInfoAddActivity.this, "房间名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomName.setFocusable(true);
						ET_roomName.requestFocus();
						return;	
					}
					roomInfo.setRoomName(ET_roomName.getText().toString());
					/*验证获取房间类型*/ 
					if(ET_roomTypeName.getText().toString().equals("")) {
						Toast.makeText(RoomInfoAddActivity.this, "房间类型输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomTypeName.setFocusable(true);
						ET_roomTypeName.requestFocus();
						return;	
					}
					roomInfo.setRoomTypeName(ET_roomTypeName.getText().toString());
					/*验证获取房间价格(元/月)*/ 
					if(ET_roomPrice.getText().toString().equals("")) {
						Toast.makeText(RoomInfoAddActivity.this, "房间价格(元/月)输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomPrice.setFocusable(true);
						ET_roomPrice.requestFocus();
						return;	
					}
					roomInfo.setRoomPrice(Float.parseFloat(ET_roomPrice.getText().toString()));
					/*验证获取总床位*/ 
					if(ET_totalBedNumber.getText().toString().equals("")) {
						Toast.makeText(RoomInfoAddActivity.this, "总床位输入不能为空!", Toast.LENGTH_LONG).show();
						ET_totalBedNumber.setFocusable(true);
						ET_totalBedNumber.requestFocus();
						return;	
					}
					roomInfo.setTotalBedNumber(Integer.parseInt(ET_totalBedNumber.getText().toString()));
					/*验证获取剩余床位*/ 
					if(ET_leftBedNum.getText().toString().equals("")) {
						Toast.makeText(RoomInfoAddActivity.this, "剩余床位输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leftBedNum.setFocusable(true);
						ET_leftBedNum.requestFocus();
						return;	
					}
					roomInfo.setLeftBedNum(Integer.parseInt(ET_leftBedNum.getText().toString()));
					/*验证获取寝室电话*/ 
					if(ET_roomTelephone.getText().toString().equals("")) {
						Toast.makeText(RoomInfoAddActivity.this, "寝室电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomTelephone.setFocusable(true);
						ET_roomTelephone.requestFocus();
						return;	
					}
					roomInfo.setRoomTelephone(ET_roomTelephone.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_roomMemo.getText().toString().equals("")) {
						Toast.makeText(RoomInfoAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomMemo.setFocusable(true);
						ET_roomMemo.requestFocus();
						return;	
					}
					roomInfo.setRoomMemo(ET_roomMemo.getText().toString());
					/*调用业务逻辑层上传房间信息信息*/
					RoomInfoAddActivity.this.setTitle("正在上传房间信息信息，稍等...");
					String result = roomInfoService.AddRoomInfo(roomInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
