package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.RoomInfo;
import com.mobileclient.util.HttpUtil;

/*房间信息管理业务逻辑层*/
public class RoomInfoService {
	/* 添加房间信息 */
	public String AddRoomInfo(RoomInfo roomInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomInfo.getRoomId() + "");
		params.put("buildingObj", roomInfo.getBuildingObj() + "");
		params.put("roomName", roomInfo.getRoomName());
		params.put("roomTypeName", roomInfo.getRoomTypeName());
		params.put("roomPrice", roomInfo.getRoomPrice() + "");
		params.put("totalBedNumber", roomInfo.getTotalBedNumber() + "");
		params.put("leftBedNum", roomInfo.getLeftBedNum() + "");
		params.put("roomTelephone", roomInfo.getRoomTelephone());
		params.put("roomMemo", roomInfo.getRoomMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询房间信息 */
	public List<RoomInfo> QueryRoomInfo(RoomInfo queryConditionRoomInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RoomInfoServlet?action=query";
		if(queryConditionRoomInfo != null) {
			urlString += "&buildingObj=" + queryConditionRoomInfo.getBuildingObj();
			urlString += "&roomName=" + URLEncoder.encode(queryConditionRoomInfo.getRoomName(), "UTF-8") + "";
			urlString += "&roomTypeName=" + URLEncoder.encode(queryConditionRoomInfo.getRoomTypeName(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RoomInfoListHandler roomInfoListHander = new RoomInfoListHandler();
		xr.setContentHandler(roomInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<RoomInfo> roomInfoList = roomInfoListHander.getRoomInfoList();
		return roomInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RoomInfo roomInfo = new RoomInfo();
				roomInfo.setRoomId(object.getInt("roomId"));
				roomInfo.setBuildingObj(object.getInt("buildingObj"));
				roomInfo.setRoomName(object.getString("roomName"));
				roomInfo.setRoomTypeName(object.getString("roomTypeName"));
				roomInfo.setRoomPrice((float) object.getDouble("roomPrice"));
				roomInfo.setTotalBedNumber(object.getInt("totalBedNumber"));
				roomInfo.setLeftBedNum(object.getInt("leftBedNum"));
				roomInfo.setRoomTelephone(object.getString("roomTelephone"));
				roomInfo.setRoomMemo(object.getString("roomMemo"));
				roomInfoList.add(roomInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomInfoList;
	}

	/* 更新房间信息 */
	public String UpdateRoomInfo(RoomInfo roomInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomInfo.getRoomId() + "");
		params.put("buildingObj", roomInfo.getBuildingObj() + "");
		params.put("roomName", roomInfo.getRoomName());
		params.put("roomTypeName", roomInfo.getRoomTypeName());
		params.put("roomPrice", roomInfo.getRoomPrice() + "");
		params.put("totalBedNumber", roomInfo.getTotalBedNumber() + "");
		params.put("leftBedNum", roomInfo.getLeftBedNum() + "");
		params.put("roomTelephone", roomInfo.getRoomTelephone());
		params.put("roomMemo", roomInfo.getRoomMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除房间信息 */
	public String DeleteRoomInfo(int roomId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "房间信息信息删除失败!";
		}
	}

	/* 根据记录编号获取房间信息对象 */
	public RoomInfo GetRoomInfo(int roomId)  {
		List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomId", roomId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RoomInfo roomInfo = new RoomInfo();
				roomInfo.setRoomId(object.getInt("roomId"));
				roomInfo.setBuildingObj(object.getInt("buildingObj"));
				roomInfo.setRoomName(object.getString("roomName"));
				roomInfo.setRoomTypeName(object.getString("roomTypeName"));
				roomInfo.setRoomPrice((float) object.getDouble("roomPrice"));
				roomInfo.setTotalBedNumber(object.getInt("totalBedNumber"));
				roomInfo.setLeftBedNum(object.getInt("leftBedNum"));
				roomInfo.setRoomTelephone(object.getString("roomTelephone"));
				roomInfo.setRoomMemo(object.getString("roomMemo"));
				roomInfoList.add(roomInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = roomInfoList.size();
		if(size>0) return roomInfoList.get(0); 
		else return null; 
	}
}
