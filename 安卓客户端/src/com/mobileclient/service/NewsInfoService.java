package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.NewsInfo;
import com.mobileclient.util.HttpUtil;

/*综合信息管理业务逻辑层*/
public class NewsInfoService {
	/* 添加综合信息 */
	public String AddNewsInfo(NewsInfo newsInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsInfo.getNewsId() + "");
		params.put("roomObj", newsInfo.getRoomObj() + "");
		params.put("infoTypeObj", newsInfo.getInfoTypeObj() + "");
		params.put("infoTitle", newsInfo.getInfoTitle());
		params.put("infoContent", newsInfo.getInfoContent());
		params.put("infoDate", newsInfo.getInfoDate().toString());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询综合信息 */
	public List<NewsInfo> QueryNewsInfo(NewsInfo queryConditionNewsInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "NewsInfoServlet?action=query";
		if(queryConditionNewsInfo != null) {
			urlString += "&roomObj=" + queryConditionNewsInfo.getRoomObj();
			urlString += "&infoTypeObj=" + queryConditionNewsInfo.getInfoTypeObj();
			urlString += "&infoTitle=" + URLEncoder.encode(queryConditionNewsInfo.getInfoTitle(), "UTF-8") + "";
			if(queryConditionNewsInfo.getInfoDate() != null) {
				urlString += "&infoDate=" + URLEncoder.encode(queryConditionNewsInfo.getInfoDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		NewsInfoListHandler newsInfoListHander = new NewsInfoListHandler();
		xr.setContentHandler(newsInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<NewsInfo> newsInfoList = newsInfoListHander.getNewsInfoList();
		return newsInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NewsInfo newsInfo = new NewsInfo();
				newsInfo.setNewsId(object.getInt("newsId"));
				newsInfo.setRoomObj(object.getInt("roomObj"));
				newsInfo.setInfoTypeObj(object.getInt("infoTypeObj"));
				newsInfo.setInfoTitle(object.getString("infoTitle"));
				newsInfo.setInfoContent(object.getString("infoContent"));
				newsInfo.setInfoDate(Timestamp.valueOf(object.getString("infoDate")));
				newsInfoList.add(newsInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsInfoList;
	}

	/* 更新综合信息 */
	public String UpdateNewsInfo(NewsInfo newsInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsInfo.getNewsId() + "");
		params.put("roomObj", newsInfo.getRoomObj() + "");
		params.put("infoTypeObj", newsInfo.getInfoTypeObj() + "");
		params.put("infoTitle", newsInfo.getInfoTitle());
		params.put("infoContent", newsInfo.getInfoContent());
		params.put("infoDate", newsInfo.getInfoDate().toString());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除综合信息 */
	public String DeleteNewsInfo(int newsId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "综合信息信息删除失败!";
		}
	}

	/* 根据记录编号获取综合信息对象 */
	public NewsInfo GetNewsInfo(int newsId)  {
		List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("newsId", newsId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "NewsInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				NewsInfo newsInfo = new NewsInfo();
				newsInfo.setNewsId(object.getInt("newsId"));
				newsInfo.setRoomObj(object.getInt("roomObj"));
				newsInfo.setInfoTypeObj(object.getInt("infoTypeObj"));
				newsInfo.setInfoTitle(object.getString("infoTitle"));
				newsInfo.setInfoContent(object.getString("infoContent"));
				newsInfo.setInfoDate(Timestamp.valueOf(object.getString("infoDate")));
				newsInfoList.add(newsInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = newsInfoList.size();
		if(size>0) return newsInfoList.get(0); 
		else return null; 
	}
}
