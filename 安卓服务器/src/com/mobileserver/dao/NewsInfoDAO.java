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
	/* 传入综合信息对象，进行综合信息的添加业务 */
	public String AddNewsInfo(NewsInfo newsInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新综合信息 */
			String sqlString = "insert into NewsInfo(roomObj,infoTypeObj,infoTitle,infoContent,infoDate) values (";
			sqlString += newsInfo.getRoomObj() + ",";
			sqlString += newsInfo.getInfoTypeObj() + ",";
			sqlString += "'" + newsInfo.getInfoTitle() + "',";
			sqlString += "'" + newsInfo.getInfoContent() + "',";
			sqlString += "'" + newsInfo.getInfoDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "综合信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "综合信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除综合信息 */
	public String DeleteNewsInfo(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NewsInfo where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "综合信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "综合信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到综合信息 */
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
	/* 更新综合信息 */
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
			result = "综合信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "综合信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
