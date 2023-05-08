package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.LiveInfo;
import com.mobileserver.util.DB;

public class LiveInfoDAO {

	public List<LiveInfo> QueryLiveInfo(String studentObj,int roomObj,Timestamp liveDate) {
		List<LiveInfo> liveInfoList = new ArrayList<LiveInfo>();
		DB db = new DB();
		String sql = "select * from LiveInfo where 1=1";
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		if (roomObj != 0)
			sql += " and roomObj=" + roomObj;
		if(liveDate!=null)
			sql += " and liveDate='" + liveDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				LiveInfo liveInfo = new LiveInfo();
				liveInfo.setLiveInfoId(rs.getInt("liveInfoId"));
				liveInfo.setStudentObj(rs.getString("studentObj"));
				liveInfo.setRoomObj(rs.getInt("roomObj"));
				liveInfo.setLiveDate(rs.getTimestamp("liveDate"));
				liveInfo.setLiveMemo(rs.getString("liveMemo"));
				liveInfoList.add(liveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return liveInfoList;
	}
	/* 传入住宿信息对象，进行住宿信息的添加业务 */
	public String AddLiveInfo(LiveInfo liveInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新住宿信息 */
			String sqlString = "insert into LiveInfo(studentObj,roomObj,liveDate,liveMemo) values (";
			sqlString += "'" + liveInfo.getStudentObj() + "',";
			sqlString += liveInfo.getRoomObj() + ",";
			sqlString += "'" + liveInfo.getLiveDate() + "',";
			sqlString += "'" + liveInfo.getLiveMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "住宿信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "住宿信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除住宿信息 */
	public String DeleteLiveInfo(int liveInfoId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LiveInfo where liveInfoId=" + liveInfoId;
			db.executeUpdate(sqlString);
			result = "住宿信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "住宿信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到住宿信息 */
	public LiveInfo GetLiveInfo(int liveInfoId) {
		LiveInfo liveInfo = null;
		DB db = new DB();
		String sql = "select * from LiveInfo where liveInfoId=" + liveInfoId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				liveInfo = new LiveInfo();
				liveInfo.setLiveInfoId(rs.getInt("liveInfoId"));
				liveInfo.setStudentObj(rs.getString("studentObj"));
				liveInfo.setRoomObj(rs.getInt("roomObj"));
				liveInfo.setLiveDate(rs.getTimestamp("liveDate"));
				liveInfo.setLiveMemo(rs.getString("liveMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return liveInfo;
	}
	/* 更新住宿信息 */
	public String UpdateLiveInfo(LiveInfo liveInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LiveInfo set ";
			sql += "studentObj='" + liveInfo.getStudentObj() + "',";
			sql += "roomObj=" + liveInfo.getRoomObj() + ",";
			sql += "liveDate='" + liveInfo.getLiveDate() + "',";
			sql += "liveMemo='" + liveInfo.getLiveMemo() + "'";
			sql += " where liveInfoId=" + liveInfo.getLiveInfoId();
			db.executeUpdate(sql);
			result = "住宿信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "住宿信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
