package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.NewsInfo;
import com.mobileclient.service.NewsInfoService;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
import com.mobileclient.domain.IntoType;
import com.mobileclient.service.IntoTypeService;
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
public class NewsInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_newsId;
	// 声明寝室房间控件
	private TextView TV_roomObj;
	// 声明信息类型控件
	private TextView TV_infoTypeObj;
	// 声明信息标题控件
	private TextView TV_infoTitle;
	// 声明信息内容控件
	private TextView TV_infoContent;
	// 声明信息日期控件
	private TextView TV_infoDate;
	/* 要保存的综合信息信息 */
	NewsInfo newsInfo = new NewsInfo(); 
	/* 综合信息管理业务逻辑层 */
	private NewsInfoService newsInfoService = new NewsInfoService();
	private RoomInfoService roomInfoService = new RoomInfoService();
	private IntoTypeService intoTypeService = new IntoTypeService();
	private int newsId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.newsinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看综合信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_newsId = (TextView) findViewById(R.id.TV_newsId);
		TV_roomObj = (TextView) findViewById(R.id.TV_roomObj);
		TV_infoTypeObj = (TextView) findViewById(R.id.TV_infoTypeObj);
		TV_infoTitle = (TextView) findViewById(R.id.TV_infoTitle);
		TV_infoContent = (TextView) findViewById(R.id.TV_infoContent);
		TV_infoDate = (TextView) findViewById(R.id.TV_infoDate);
		Bundle extras = this.getIntent().getExtras();
		newsId = extras.getInt("newsId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NewsInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    newsInfo = newsInfoService.GetNewsInfo(newsId); 
		this.TV_newsId.setText(newsInfo.getNewsId() + "");
		RoomInfo roomObj = roomInfoService.GetRoomInfo(newsInfo.getRoomObj());
		this.TV_roomObj.setText(roomObj.getRoomName());
		IntoType infoTypeObj = intoTypeService.GetIntoType(newsInfo.getInfoTypeObj());
		this.TV_infoTypeObj.setText(infoTypeObj.getInfoTypeName());
		this.TV_infoTitle.setText(newsInfo.getInfoTitle());
		this.TV_infoContent.setText(newsInfo.getInfoContent());
		Date infoDate = new Date(newsInfo.getInfoDate().getTime());
		String infoDateStr = (infoDate.getYear() + 1900) + "-" + (infoDate.getMonth()+1) + "-" + infoDate.getDate();
		this.TV_infoDate.setText(infoDateStr);
	} 
}
