package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.BuildingInfo;
import com.mobileclient.service.BuildingInfoService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.BuildingInfoSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class BuildingInfoListActivity extends Activity {
	BuildingInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int buildingId;
	/* 宿舍信息操作业务逻辑层对象 */
	BuildingInfoService buildingInfoService = new BuildingInfoService();
	/*保存查询参数条件的宿舍信息对象*/
	private BuildingInfo queryConditionBuildingInfo;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.buildinginfo_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(BuildingInfoListActivity.this, BuildingInfoQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("宿舍信息查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(BuildingInfoListActivity.this, BuildingInfoAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionBuildingInfo = (BuildingInfo)extras.getSerializable("queryConditionBuildingInfo");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionBuildingInfo = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new BuildingInfoSimpleAdapter(BuildingInfoListActivity.this, list,
	        					R.layout.buildinginfo_list_item,
	        					new String[] { "buildingId","areaObj","buildingName","manageMan","telephone" },
	        					new int[] { R.id.tv_buildingId,R.id.tv_areaObj,R.id.tv_buildingName,R.id.tv_manageMan,R.id.tv_telephone,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(buildingInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int buildingId = Integer.parseInt(list.get(arg2).get("buildingId").toString());
            	Intent intent = new Intent();
            	intent.setClass(BuildingInfoListActivity.this, BuildingInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("buildingId", buildingId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener buildingInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑宿舍信息信息"); 
			menu.add(0, 1, 0, "删除宿舍信息信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑宿舍信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			buildingId = Integer.parseInt(list.get(position).get("buildingId").toString());
			Intent intent = new Intent();
			intent.setClass(BuildingInfoListActivity.this, BuildingInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("buildingId", buildingId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除宿舍信息信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取记录编号
			buildingId = Integer.parseInt(list.get(position).get("buildingId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(BuildingInfoListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = buildingInfoService.DeleteBuildingInfo(buildingId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询宿舍信息信息 */
			List<BuildingInfo> buildingInfoList = buildingInfoService.QueryBuildingInfo(queryConditionBuildingInfo);
			for (int i = 0; i < buildingInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("buildingId", buildingInfoList.get(i).getBuildingId());
				map.put("areaObj", buildingInfoList.get(i).getAreaObj());
				map.put("buildingName", buildingInfoList.get(i).getBuildingName());
				map.put("manageMan", buildingInfoList.get(i).getManageMan());
				map.put("telephone", buildingInfoList.get(i).getTelephone());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
