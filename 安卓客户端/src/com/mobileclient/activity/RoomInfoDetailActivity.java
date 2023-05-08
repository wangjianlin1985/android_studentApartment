package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.service.BuildingInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class RoomInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_roomId;
	// 声明所在宿舍控件
	private TextView TV_buildingObj;
	// 声明房间名称控件
	private TextView TV_roomName;
	// 声明房间类型控件
	private TextView TV_roomTypeName;
	// 声明房间价格(元/月)控件
	private TextView TV_roomPrice;
	// 声明总床位控件
	private TextView TV_totalBedNumber;
	// 声明剩余床位控件
	private TextView TV_leftBedNum;
	// 声明寝室电话控件
	private TextView TV_roomTelephone;
	// 声明附加信息控件
	private TextView TV_roomMemo;
	/* 要保存的房间信息信息 */
	RoomInfo roomInfo = new RoomInfo(); 
	/* 房间信息管理业务逻辑层 */
	private RoomInfoService roomInfoService = new RoomInfoService();
	private BuildingInfoService buildingInfoService = new BuildingInfoService();
	private int roomId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.roominfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看房间信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_roomId = (TextView) findViewById(R.id.TV_roomId);
		TV_buildingObj = (TextView) findViewById(R.id.TV_buildingObj);
		TV_roomName = (TextView) findViewById(R.id.TV_roomName);
		TV_roomTypeName = (TextView) findViewById(R.id.TV_roomTypeName);
		TV_roomPrice = (TextView) findViewById(R.id.TV_roomPrice);
		TV_totalBedNumber = (TextView) findViewById(R.id.TV_totalBedNumber);
		TV_leftBedNum = (TextView) findViewById(R.id.TV_leftBedNum);
		TV_roomTelephone = (TextView) findViewById(R.id.TV_roomTelephone);
		TV_roomMemo = (TextView) findViewById(R.id.TV_roomMemo);
		Bundle extras = this.getIntent().getExtras();
		roomId = extras.getInt("roomId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RoomInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    roomInfo = roomInfoService.GetRoomInfo(roomId); 
		this.TV_roomId.setText(roomInfo.getRoomId() + "");
		BuildingInfo buildingObj = buildingInfoService.GetBuildingInfo(roomInfo.getBuildingObj());
		this.TV_buildingObj.setText(buildingObj.getBuildingName());
		this.TV_roomName.setText(roomInfo.getRoomName());
		this.TV_roomTypeName.setText(roomInfo.getRoomTypeName());
		this.TV_roomPrice.setText(roomInfo.getRoomPrice() + "");
		this.TV_totalBedNumber.setText(roomInfo.getTotalBedNumber() + "");
		this.TV_leftBedNum.setText(roomInfo.getLeftBedNum() + "");
		this.TV_roomTelephone.setText(roomInfo.getRoomTelephone());
		this.TV_roomMemo.setText(roomInfo.getRoomMemo());
	} 
}
