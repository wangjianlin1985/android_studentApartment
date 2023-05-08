package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.NewsInfo;
import com.mobileclient.service.NewsInfoService;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
import com.mobileclient.domain.IntoType;
import com.mobileclient.service.IntoTypeService;
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
public class NewsInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明寝室房间下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<RoomInfo> roomInfoList = null;
	/*寝室房间管理业务逻辑层*/
	private RoomInfoService roomInfoService = new RoomInfoService();
	// 声明信息类型下拉框
	private Spinner spinner_infoTypeObj;
	private ArrayAdapter<String> infoTypeObj_adapter;
	private static  String[] infoTypeObj_ShowText  = null;
	private List<IntoType> intoTypeList = null;
	/*信息类型管理业务逻辑层*/
	private IntoTypeService intoTypeService = new IntoTypeService();
	// 声明信息标题输入框
	private EditText ET_infoTitle;
	// 声明信息内容输入框
	private EditText ET_infoContent;
	// 出版信息日期控件
	private DatePicker dp_infoDate;
	protected String carmera_path;
	/*要保存的综合信息信息*/
	NewsInfo newsInfo = new NewsInfo();
	/*综合信息管理业务逻辑层*/
	private NewsInfoService newsInfoService = new NewsInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.newsinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加综合信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的寝室房间
		try {
			roomInfoList = roomInfoService.QueryRoomInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int roomInfoCount = roomInfoList.size();
		roomObj_ShowText = new String[roomInfoCount];
		for(int i=0;i<roomInfoCount;i++) { 
			roomObj_ShowText[i] = roomInfoList.get(i).getRoomName();
		}
		// 将可选内容与ArrayAdapter连接起来
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// 设置下拉列表的风格
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomObj.setAdapter(roomObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				newsInfo.setRoomObj(roomInfoList.get(arg2).getRoomId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomObj.setVisibility(View.VISIBLE);
		spinner_infoTypeObj = (Spinner) findViewById(R.id.Spinner_infoTypeObj);
		// 获取所有的信息类型
		try {
			intoTypeList = intoTypeService.QueryIntoType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int intoTypeCount = intoTypeList.size();
		infoTypeObj_ShowText = new String[intoTypeCount];
		for(int i=0;i<intoTypeCount;i++) { 
			infoTypeObj_ShowText[i] = intoTypeList.get(i).getInfoTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		infoTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, infoTypeObj_ShowText);
		// 设置下拉列表的风格
		infoTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_infoTypeObj.setAdapter(infoTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_infoTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				newsInfo.setInfoTypeObj(intoTypeList.get(arg2).getTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_infoTypeObj.setVisibility(View.VISIBLE);
		ET_infoTitle = (EditText) findViewById(R.id.ET_infoTitle);
		ET_infoContent = (EditText) findViewById(R.id.ET_infoContent);
		dp_infoDate = (DatePicker)this.findViewById(R.id.dp_infoDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加综合信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取信息标题*/ 
					if(ET_infoTitle.getText().toString().equals("")) {
						Toast.makeText(NewsInfoAddActivity.this, "信息标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_infoTitle.setFocusable(true);
						ET_infoTitle.requestFocus();
						return;	
					}
					newsInfo.setInfoTitle(ET_infoTitle.getText().toString());
					/*验证获取信息内容*/ 
					if(ET_infoContent.getText().toString().equals("")) {
						Toast.makeText(NewsInfoAddActivity.this, "信息内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_infoContent.setFocusable(true);
						ET_infoContent.requestFocus();
						return;	
					}
					newsInfo.setInfoContent(ET_infoContent.getText().toString());
					/*获取信息日期*/
					Date infoDate = new Date(dp_infoDate.getYear()-1900,dp_infoDate.getMonth(),dp_infoDate.getDayOfMonth());
					newsInfo.setInfoDate(new Timestamp(infoDate.getTime()));
					/*调用业务逻辑层上传综合信息信息*/
					NewsInfoAddActivity.this.setTitle("正在上传综合信息信息，稍等...");
					String result = newsInfoService.AddNewsInfo(newsInfo);
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
