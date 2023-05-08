package com.mobileclient.activity;

import java.util.Date;
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
public class IntoTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_typeId;
	// 声明信息类别控件
	private TextView TV_infoTypeName;
	/* 要保存的信息类型信息 */
	IntoType intoType = new IntoType(); 
	/* 信息类型管理业务逻辑层 */
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
		setContentView(R.layout.intotype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看信息类型详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_typeId = (TextView) findViewById(R.id.TV_typeId);
		TV_infoTypeName = (TextView) findViewById(R.id.TV_infoTypeName);
		Bundle extras = this.getIntent().getExtras();
		typeId = extras.getInt("typeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				IntoTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    intoType = intoTypeService.GetIntoType(typeId); 
		this.TV_typeId.setText(intoType.getTypeId() + "");
		this.TV_infoTypeName.setText(intoType.getInfoTypeName());
	} 
}
