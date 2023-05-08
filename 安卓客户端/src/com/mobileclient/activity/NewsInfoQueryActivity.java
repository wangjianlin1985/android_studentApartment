package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.NewsInfo;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
import com.mobileclient.domain.IntoType;
import com.mobileclient.service.IntoTypeService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class NewsInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明寝室房间下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<RoomInfo> roomInfoList = null; 
	/*房间信息管理业务逻辑层*/
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
	// 信息日期控件
	private DatePicker dp_infoDate;
	private CheckBox cb_infoDate;
	/*查询过滤条件保存到这个对象中*/
	private NewsInfo queryConditionNewsInfo = new NewsInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.newsinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置综合信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的房间信息
		try {
			roomInfoList = roomInfoService.QueryRoomInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int roomInfoCount = roomInfoList.size();
		roomObj_ShowText = new String[roomInfoCount+1];
		roomObj_ShowText[0] = "不限制";
		for(int i=1;i<=roomInfoCount;i++) { 
			roomObj_ShowText[i] = roomInfoList.get(i-1).getRoomName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// 设置寝室房间下拉列表的风格
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomObj.setAdapter(roomObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionNewsInfo.setRoomObj(roomInfoList.get(arg2-1).getRoomId()); 
				else
					queryConditionNewsInfo.setRoomObj(0);
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
		infoTypeObj_ShowText = new String[intoTypeCount+1];
		infoTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=intoTypeCount;i++) { 
			infoTypeObj_ShowText[i] = intoTypeList.get(i-1).getInfoTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		infoTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, infoTypeObj_ShowText);
		// 设置信息类型下拉列表的风格
		infoTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_infoTypeObj.setAdapter(infoTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_infoTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionNewsInfo.setInfoTypeObj(intoTypeList.get(arg2-1).getTypeId()); 
				else
					queryConditionNewsInfo.setInfoTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_infoTypeObj.setVisibility(View.VISIBLE);
		ET_infoTitle = (EditText) findViewById(R.id.ET_infoTitle);
		dp_infoDate = (DatePicker) findViewById(R.id.dp_infoDate);
		cb_infoDate = (CheckBox) findViewById(R.id.cb_infoDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionNewsInfo.setInfoTitle(ET_infoTitle.getText().toString());
					if(cb_infoDate.isChecked()) {
						/*获取信息日期*/
						Date infoDate = new Date(dp_infoDate.getYear()-1900,dp_infoDate.getMonth(),dp_infoDate.getDayOfMonth());
						queryConditionNewsInfo.setInfoDate(new Timestamp(infoDate.getTime()));
					} else {
						queryConditionNewsInfo.setInfoDate(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionNewsInfo", queryConditionNewsInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
