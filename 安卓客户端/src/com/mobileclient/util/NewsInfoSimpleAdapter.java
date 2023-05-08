package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.RoomInfoService;
import com.mobileclient.service.IntoTypeService;
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

public class NewsInfoSimpleAdapter extends SimpleAdapter { 
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

    public NewsInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.newsinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_newsId = (TextView)convertView.findViewById(R.id.tv_newsId);
	  holder.tv_roomObj = (TextView)convertView.findViewById(R.id.tv_roomObj);
	  holder.tv_infoTypeObj = (TextView)convertView.findViewById(R.id.tv_infoTypeObj);
	  holder.tv_infoTitle = (TextView)convertView.findViewById(R.id.tv_infoTitle);
	  holder.tv_infoDate = (TextView)convertView.findViewById(R.id.tv_infoDate);
	  /*设置各个控件的展示内容*/
	  holder.tv_newsId.setText("记录编号：" + mData.get(position).get("newsId").toString());
	  holder.tv_roomObj.setText("寝室房间：" + (new RoomInfoService()).GetRoomInfo(Integer.parseInt(mData.get(position).get("roomObj").toString())).getRoomName());
	  holder.tv_infoTypeObj.setText("信息类型：" + (new IntoTypeService()).GetIntoType(Integer.parseInt(mData.get(position).get("infoTypeObj").toString())).getInfoTypeName());
	  holder.tv_infoTitle.setText("信息标题：" + mData.get(position).get("infoTitle").toString());
	  try {holder.tv_infoDate.setText("信息日期：" + mData.get(position).get("infoDate").toString().substring(0, 10));} catch(Exception ex){}
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_newsId;
    	TextView tv_roomObj;
    	TextView tv_infoTypeObj;
    	TextView tv_infoTitle;
    	TextView tv_infoDate;
    }
} 
