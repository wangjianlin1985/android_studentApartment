package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.LiveInfo;
import com.mobileclient.service.LiveInfoService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
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
public class LiveInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_liveInfoId;
	// 声明学生控件
	private TextView TV_studentObj;
	// 声明所在房间控件
	private TextView TV_roomObj;
	// 声明入住日期控件
	private TextView TV_liveDate;
	// 声明附加信息控件
	private TextView TV_liveMemo;
	/* 要保存的住宿信息信息 */
	LiveInfo liveInfo = new LiveInfo(); 
	/* 住宿信息管理业务逻辑层 */
	private LiveInfoService liveInfoService = new LiveInfoService();
	private StudentService studentService = new StudentService();
	private RoomInfoService roomInfoService = new RoomInfoService();
	private int liveInfoId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.liveinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看住宿信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_liveInfoId = (TextView) findViewById(R.id.TV_liveInfoId);
		TV_studentObj = (TextView) findViewById(R.id.TV_studentObj);
		TV_roomObj = (TextView) findViewById(R.id.TV_roomObj);
		TV_liveDate = (TextView) findViewById(R.id.TV_liveDate);
		TV_liveMemo = (TextView) findViewById(R.id.TV_liveMemo);
		Bundle extras = this.getIntent().getExtras();
		liveInfoId = extras.getInt("liveInfoId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LiveInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    liveInfo = liveInfoService.GetLiveInfo(liveInfoId); 
		this.TV_liveInfoId.setText(liveInfo.getLiveInfoId() + "");
		Student studentObj = studentService.GetStudent(liveInfo.getStudentObj());
		this.TV_studentObj.setText(studentObj.getStudentName());
		RoomInfo roomObj = roomInfoService.GetRoomInfo(liveInfo.getRoomObj());
		this.TV_roomObj.setText(roomObj.getRoomName());
		Date liveDate = new Date(liveInfo.getLiveDate().getTime());
		String liveDateStr = (liveDate.getYear() + 1900) + "-" + (liveDate.getMonth()+1) + "-" + liveDate.getDate();
		this.TV_liveDate.setText(liveDateStr);
		this.TV_liveMemo.setText(liveInfo.getLiveMemo());
	} 
}
