package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LiveInfo;
import com.mobileclient.util.HttpUtil;

/*住宿信息管理业务逻辑层*/
public class LiveInfoService {
	/* 添加住宿信息 */
	public String AddLiveInfo(LiveInfo liveInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("liveInfoId", liveInfo.getLiveInfoId() + "");
		params.put("studentObj", liveInfo.getStudentObj());
		params.put("roomObj", liveInfo.getRoomObj() + "");
		params.put("liveDate", liveInfo.getLiveDate().toString());
		params.put("liveMemo", liveInfo.getLiveMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LiveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询住宿信息 */
	public List<LiveInfo> QueryLiveInfo(LiveInfo queryConditionLiveInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LiveInfoServlet?action=query";
		if(queryConditionLiveInfo != null) {
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionLiveInfo.getStudentObj(), "UTF-8") + "";
			urlString += "&roomObj=" + queryConditionLiveInfo.getRoomObj();
			if(queryConditionLiveInfo.getLiveDate() != null) {
				urlString += "&liveDate=" + URLEncoder.encode(queryConditionLiveInfo.getLiveDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LiveInfoListHandler liveInfoListHander = new LiveInfoListHandler();
		xr.setContentHandler(liveInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LiveInfo> liveInfoList = liveInfoListHander.getLiveInfoList();
		return liveInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LiveInfo> liveInfoList = new ArrayList<LiveInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LiveInfo liveInfo = new LiveInfo();
				liveInfo.setLiveInfoId(object.getInt("liveInfoId"));
				liveInfo.setStudentObj(object.getString("studentObj"));
				liveInfo.setRoomObj(object.getInt("roomObj"));
				liveInfo.setLiveDate(Timestamp.valueOf(object.getString("liveDate")));
				liveInfo.setLiveMemo(object.getString("liveMemo"));
				liveInfoList.add(liveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return liveInfoList;
	}

	/* 更新住宿信息 */
	public String UpdateLiveInfo(LiveInfo liveInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("liveInfoId", liveInfo.getLiveInfoId() + "");
		params.put("studentObj", liveInfo.getStudentObj());
		params.put("roomObj", liveInfo.getRoomObj() + "");
		params.put("liveDate", liveInfo.getLiveDate().toString());
		params.put("liveMemo", liveInfo.getLiveMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LiveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除住宿信息 */
	public String DeleteLiveInfo(int liveInfoId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("liveInfoId", liveInfoId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LiveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "住宿信息信息删除失败!";
		}
	}

	/* 根据记录编号获取住宿信息对象 */
	public LiveInfo GetLiveInfo(int liveInfoId)  {
		List<LiveInfo> liveInfoList = new ArrayList<LiveInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("liveInfoId", liveInfoId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LiveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LiveInfo liveInfo = new LiveInfo();
				liveInfo.setLiveInfoId(object.getInt("liveInfoId"));
				liveInfo.setStudentObj(object.getString("studentObj"));
				liveInfo.setRoomObj(object.getInt("roomObj"));
				liveInfo.setLiveDate(Timestamp.valueOf(object.getString("liveDate")));
				liveInfo.setLiveMemo(object.getString("liveMemo"));
				liveInfoList.add(liveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = liveInfoList.size();
		if(size>0) return liveInfoList.get(0); 
		else return null; 
	}
}
