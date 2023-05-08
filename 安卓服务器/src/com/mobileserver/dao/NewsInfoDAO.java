package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.NewsInfo;
import com.mobileserver.util.DB;

public class NewsInfoDAO {

	public List<NewsInfo> QueryNewsInfo(int roomObj,int infoTypeObj,String infoTitle,Timestamp infoDate) {
		List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
		DB db = new DB();
		String sql = "select * from NewsInfo where 1=1";
		if (roomObj != 0)
			sql += " and roomObj=" + roomObj;
		if (infoTypeObj != 0)
			sql += " and infoTypeObj=" + infoTypeObj;
		if (!infoTitle.equals(""))
			sql += " and infoTitle like '%" + infoTitle + "%'";
		if(infoDate!=null)
			sql += " and infoDate='" + infoDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				NewsInfo newsInfo = new NewsInfo();
				newsInfo.setNewsId(rs.getInt("newsId"));
				newsInfo.setRoomObj(rs.getInt("roomObj"));
				newsInfo.setInfoTypeObj(rs.getInt("infoTypeObj"));
				newsInfo.setInfoTitle(rs.getString("infoTitle"));
				newsInfo.setInfoContent(rs.getString("infoContent"));
				newsInfo.setInfoDate(rs.getTimestamp("infoDate"));
				newsInfoList.add(newsInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsInfoList;
	}
	/* �����ۺ���Ϣ���󣬽����ۺ���Ϣ�����ҵ�� */
	public String AddNewsInfo(NewsInfo newsInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������ۺ���Ϣ */
			String sqlString = "insert into NewsInfo(roomObj,infoTypeObj,infoTitle,infoContent,infoDate) values (";
			sqlString += newsInfo.getRoomObj() + ",";
			sqlString += newsInfo.getInfoTypeObj() + ",";
			sqlString += "'" + newsInfo.getInfoTitle() + "',";
			sqlString += "'" + newsInfo.getInfoContent() + "',";
			sqlString += "'" + newsInfo.getInfoDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�ۺ���Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ۺ���Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���ۺ���Ϣ */
	public String DeleteNewsInfo(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NewsInfo where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "�ۺ���Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ۺ���Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ���ۺ���Ϣ */
	public NewsInfo GetNewsInfo(int newsId) {
		NewsInfo newsInfo = null;
		DB db = new DB();
		String sql = "select * from NewsInfo where newsId=" + newsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				newsInfo = new NewsInfo();
				newsInfo.setNewsId(rs.getInt("newsId"));
				newsInfo.setRoomObj(rs.getInt("roomObj"));
				newsInfo.setInfoTypeObj(rs.getInt("infoTypeObj"));
				newsInfo.setInfoTitle(rs.getString("infoTitle"));
				newsInfo.setInfoContent(rs.getString("infoContent"));
				newsInfo.setInfoDate(rs.getTimestamp("infoDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsInfo;
	}
	/* �����ۺ���Ϣ */
	public String UpdateNewsInfo(NewsInfo newsInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update NewsInfo set ";
			sql += "roomObj=" + newsInfo.getRoomObj() + ",";
			sql += "infoTypeObj=" + newsInfo.getInfoTypeObj() + ",";
			sql += "infoTitle='" + newsInfo.getInfoTitle() + "',";
			sql += "infoContent='" + newsInfo.getInfoContent() + "',";
			sql += "infoDate='" + newsInfo.getInfoDate() + "'";
			sql += " where newsId=" + newsInfo.getNewsId();
			db.executeUpdate(sql);
			result = "�ۺ���Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ۺ���Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
