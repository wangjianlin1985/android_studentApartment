package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.IntoType;
import com.mobileclient.util.HttpUtil;

/*信息类型管理业务逻辑层*/
public class IntoTypeService {
	/* 添加信息类型 */
	public String AddIntoType(IntoType intoType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", intoType.getTypeId() + "");
		params.put("infoTypeName", intoType.getInfoTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "IntoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询信息类型 */
	public List<IntoType> QueryIntoType(IntoType queryConditionIntoType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "IntoTypeServlet?action=query";
		if(queryConditionIntoType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		IntoTypeListHandler intoTypeListHander = new IntoTypeListHandler();
		xr.setContentHandler(intoTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<IntoType> intoTypeList = intoTypeListHander.getIntoTypeList();
		return intoTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<IntoType> intoTypeList = new ArrayList<IntoType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				IntoType intoType = new IntoType();
				intoType.setTypeId(object.getInt("typeId"));
				intoType.setInfoTypeName(object.getString("infoTypeName"));
				intoTypeList.add(intoType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return intoTypeList;
	}

	/* 更新信息类型 */
	public String UpdateIntoType(IntoType intoType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", intoType.getTypeId() + "");
		params.put("infoTypeName", intoType.getInfoTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "IntoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除信息类型 */
	public String DeleteIntoType(int typeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "IntoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "信息类型信息删除失败!";
		}
	}

	/* 根据记录编号获取信息类型对象 */
	public IntoType GetIntoType(int typeId)  {
		List<IntoType> intoTypeList = new ArrayList<IntoType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "IntoTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				IntoType intoType = new IntoType();
				intoType.setTypeId(object.getInt("typeId"));
				intoType.setInfoTypeName(object.getString("infoTypeName"));
				intoTypeList.add(intoType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = intoTypeList.size();
		if(size>0) return intoTypeList.get(0); 
		else return null; 
	}
}
