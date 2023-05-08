package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class BuildingInfoSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public BuildingInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.buildinginfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_buildingId = (TextView)convertView.findViewById(R.id.tv_buildingId);
	  holder.tv_areaObj = (TextView)convertView.findViewById(R.id.tv_areaObj);
	  holder.tv_buildingName = (TextView)convertView.findViewById(R.id.tv_buildingName);
	  holder.tv_manageMan = (TextView)convertView.findViewById(R.id.tv_manageMan);
	  holder.tv_telephone = (TextView)convertView.findViewById(R.id.tv_telephone);
	  /*设置各个控件的展示内容*/
	  holder.tv_buildingId.setText("记录编号：" + mData.get(position).get("buildingId").toString());
	  holder.tv_areaObj.setText("所在校区：" + mData.get(position).get("areaObj").toString());
	  holder.tv_buildingName.setText("宿舍名称：" + mData.get(position).get("buildingName").toString());
	  holder.tv_manageMan.setText("管理员：" + mData.get(position).get("manageMan").toString());
	  holder.tv_telephone.setText("门卫电话：" + mData.get(position).get("telephone").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_buildingId;
    	TextView tv_areaObj;
    	TextView tv_buildingName;
    	TextView tv_manageMan;
    	TextView tv_telephone;
    }
} 
