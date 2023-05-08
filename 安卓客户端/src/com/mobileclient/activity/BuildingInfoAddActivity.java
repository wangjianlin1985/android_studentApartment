package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class BuildingInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明所在校区输入框
	private EditText ET_areaObj;
	// 声明宿舍名称输入框
	private EditText ET_buildingName;
	// 声明管理员输入框
	private EditText ET_manageMan;
	// 声明门卫电话输入框
	private EditText ET_telephone;
	protected String carmera_path;
	/*要保存的宿舍信息信息*/
	BuildingInfo buildingInfo = new BuildingInfo();
	/*宿舍信息管理业务逻辑层*/
	private BuildingInfoService buildingInfoService = new BuildingInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.buildinginfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加宿舍信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_areaObj = (EditText) findViewById(R.id.ET_areaObj);
		ET_buildingName = (EditText) findViewById(R.id.ET_buildingName);
		ET_manageMan = (EditText) findViewById(R.id.ET_manageMan);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加宿舍信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取所在校区*/ 
					if(ET_areaObj.getText().toString().equals("")) {
						Toast.makeText(BuildingInfoAddActivity.this, "所在校区输入不能为空!", Toast.LENGTH_LONG).show();
						ET_areaObj.setFocusable(true);
						ET_areaObj.requestFocus();
						return;	
					}
					buildingInfo.setAreaObj(ET_areaObj.getText().toString());
					/*验证获取宿舍名称*/ 
					if(ET_buildingName.getText().toString().equals("")) {
						Toast.makeText(BuildingInfoAddActivity.this, "宿舍名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_buildingName.setFocusable(true);
						ET_buildingName.requestFocus();
						return;	
					}
					buildingInfo.setBuildingName(ET_buildingName.getText().toString());
					/*验证获取管理员*/ 
					if(ET_manageMan.getText().toString().equals("")) {
						Toast.makeText(BuildingInfoAddActivity.this, "管理员输入不能为空!", Toast.LENGTH_LONG).show();
						ET_manageMan.setFocusable(true);
						ET_manageMan.requestFocus();
						return;	
					}
					buildingInfo.setManageMan(ET_manageMan.getText().toString());
					/*验证获取门卫电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(BuildingInfoAddActivity.this, "门卫电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					buildingInfo.setTelephone(ET_telephone.getText().toString());
					/*调用业务逻辑层上传宿舍信息信息*/
					BuildingInfoAddActivity.this.setTitle("正在上传宿舍信息信息，稍等...");
					String result = buildingInfoService.AddBuildingInfo(buildingInfo);
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
