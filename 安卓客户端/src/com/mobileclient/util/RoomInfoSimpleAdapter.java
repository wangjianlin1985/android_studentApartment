package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.BuildingInfoService;
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

public class RoomInfoSimpleAdapter extends SimpleAdapter { 
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

    public RoomInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.roominfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_roomId = (TextView)convertView.findViewById(R.id.tv_roomId);
	  holder.tv_buildingObj = (TextView)convertView.findViewById(R.id.tv_buildingObj);
	  holder.tv_roomName = (TextView)convertView.findViewById(R.id.tv_roomName);
	  holder.tv_roomTypeName = (TextView)convertView.findViewById(R.id.tv_roomTypeName);
	  holder.tv_roomPrice = (TextView)convertView.findViewById(R.id.tv_roomPrice);
	  /*设置各个控件的展示内容*/
	  holder.tv_roomId.setText("记录编号：" + mData.get(position).get("roomId").toString());
	  holder.tv_buildingObj.setText("所在宿舍：" + (new BuildingInfoService()).GetBuildingInfo(Integer.parseInt(mData.get(position).get("buildingObj").toString())).getBuildingName());
	  holder.tv_roomName.setText("房间名称：" + mData.get(position).get("roomName").toString());
	  holder.tv_roomTypeName.setText("房间类型：" + mData.get(position).get("roomTypeName").toString());
	  holder.tv_roomPrice.setText("房间价格(元/月)：" + mData.get(position).get("roomPrice").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_roomId;
    	TextView tv_buildingObj;
    	TextView tv_roomName;
    	TextView tv_roomTypeName;
    	TextView tv_roomPrice;
    }
} 
