package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
public class IntoTypeAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明信息类别输入框
	private EditText ET_infoTypeName;
	protected String carmera_path;
	/*要保存的信息类型信息*/
	IntoType intoType = new IntoType();
	/*信息类型管理业务逻辑层*/
	private IntoTypeService intoTypeService = new IntoTypeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.intotype_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加信息类型");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_infoTypeName = (EditText) findViewById(R.id.ET_infoTypeName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加信息类型按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取信息类别*/ 
					if(ET_infoTypeName.getText().toString().equals("")) {
						Toast.makeText(IntoTypeAddActivity.this, "信息类别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_infoTypeName.setFocusable(true);
						ET_infoTypeName.requestFocus();
						return;	
					}
					intoType.setInfoTypeName(ET_infoTypeName.getText().toString());
					/*调用业务逻辑层上传信息类型信息*/
					IntoTypeAddActivity.this.setTitle("正在上传信息类型信息，稍等...");
					String result = intoTypeService.AddIntoType(intoType);
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
