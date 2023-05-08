package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.domain.ClassInfo;
import com.mobileclient.service.ClassInfoService;
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
public class StudentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明学号控件
	private TextView TV_studentNumber;
	// 声明姓名控件
	private TextView TV_studentName;
	// 声明性别控件
	private TextView TV_sex;
	// 声明所在班级控件
	private TextView TV_classInfoId;
	// 声明出生日期控件
	private TextView TV_birthday;
	// 声明政治面貌控件
	private TextView TV_zzmm;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明家庭地址控件
	private TextView TV_address;
	// 声明学生照片图片框
	private ImageView iv_studentPhoto;
	/* 要保存的学生信息信息 */
	Student student = new Student(); 
	/* 学生信息管理业务逻辑层 */
	private StudentService studentService = new StudentService();
	private ClassInfoService classInfoService = new ClassInfoService();
	private String studentNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.student_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学生信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_studentNumber = (TextView) findViewById(R.id.TV_studentNumber);
		TV_studentName = (TextView) findViewById(R.id.TV_studentName);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_classInfoId = (TextView) findViewById(R.id.TV_classInfoId);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_zzmm = (TextView) findViewById(R.id.TV_zzmm);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto); 
		Bundle extras = this.getIntent().getExtras();
		studentNumber = extras.getString("studentNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StudentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    student = studentService.GetStudent(studentNumber); 
		this.TV_studentNumber.setText(student.getStudentNumber());
		this.TV_studentName.setText(student.getStudentName());
		this.TV_sex.setText(student.getSex());
		ClassInfo classInfoId = classInfoService.GetClassInfo(student.getClassInfoId());
		this.TV_classInfoId.setText(classInfoId.getClassName());
		Date birthday = new Date(student.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		this.TV_zzmm.setText(student.getZzmm());
		this.TV_telephone.setText(student.getTelephone());
		this.TV_address.setText(student.getAddress());
		byte[] studentPhoto_data = null;
		try {
			// 获取图片数据
			studentPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + student.getStudentPhoto());
			Bitmap studentPhoto = BitmapFactory.decodeByteArray(studentPhoto_data, 0,studentPhoto_data.length);
			this.iv_studentPhoto.setImageBitmap(studentPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
