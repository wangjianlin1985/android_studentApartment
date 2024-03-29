package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class IntoTypeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_typeId;
	// 声明信息类别输入框
	private EditText ET_infoTypeName;
	protected String carmera_path;
	/*要保存的信息类型信息*/
	IntoType intoType = new IntoType();
	/*信息类型管理业务逻辑层*/
	private IntoTypeService intoTypeService = new IntoTypeService();

	private int typeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.intotype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑信息类型信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_typeId = (TextView) findViewById(R.id.TV_typeId);
		ET_infoTypeName = (EditText) findViewById(R.id.ET_infoTypeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		typeId = extras.getInt("typeId");
		/*单击修改信息类型按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取信息类别*/ 
					if(ET_infoTypeName.getText().toString().equals("")) {
						Toast.makeText(IntoTypeEditActivity.this, "信息类别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_infoTypeName.setFocusable(true);
						ET_infoTypeName.requestFocus();
						return;	
					}
					intoType.setInfoTypeName(ET_infoTypeName.getText().toString());
					/*调用业务逻辑层上传信息类型信息*/
					IntoTypeEditActivity.this.setTitle("正在更新信息类型信息，稍等...");
					String result = intoTypeService.UpdateIntoType(intoType);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    intoType = intoTypeService.GetIntoType(typeId);
		this.TV_typeId.setText(typeId+"");
		this.ET_infoTypeName.setText(intoType.getInfoTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
