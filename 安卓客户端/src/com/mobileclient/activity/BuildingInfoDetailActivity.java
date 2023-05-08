package com.mobileclient.activity;

import java.util.Date;
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
public class BuildingInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_buildingId;
	// 声明所在校区控件
	private TextView TV_areaObj;
	// 声明宿舍名称控件
	private TextView TV_buildingName;
	// 声明管理员控件
	private TextView TV_manageMan;
	// 声明门卫电话控件
	private TextView TV_telephone;
	/* 要保存的宿舍信息信息 */
	BuildingInfo buildingInfo = new BuildingInfo(); 
	/* 宿舍信息管理业务逻辑层 */
	private BuildingInfoService buildingInfoService = new BuildingInfoService();
	private int buildingId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.buildinginfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看宿舍信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_buildingId = (TextView) findViewById(R.id.TV_buildingId);
		TV_areaObj = (TextView) findViewById(R.id.TV_areaObj);
		TV_buildingName = (TextView) findViewById(R.id.TV_buildingName);
		TV_manageMan = (TextView) findViewById(R.id.TV_manageMan);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		Bundle extras = this.getIntent().getExtras();
		buildingId = extras.getInt("buildingId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BuildingInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    buildingInfo = buildingInfoService.GetBuildingInfo(buildingId); 
		this.TV_buildingId.setText(buildingInfo.getBuildingId() + "");
		this.TV_areaObj.setText(buildingInfo.getAreaObj());
		this.TV_buildingName.setText(buildingInfo.getBuildingName());
		this.TV_manageMan.setText(buildingInfo.getManageMan());
		this.TV_telephone.setText(buildingInfo.getTelephone());
	} 
}
