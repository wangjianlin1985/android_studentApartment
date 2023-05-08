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
	/* ����ס����Ϣ���󣬽���ס����Ϣ�����ҵ�� */
	public String AddLiveInfo(LiveInfo liveInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ס����Ϣ */
			String sqlString = "insert into LiveInfo(studentObj,roomObj,liveDate,liveMemo) values (";
			sqlString += "'" + liveInfo.getStudentObj() + "',";
			sqlString += liveInfo.getRoomObj() + ",";
			sqlString += "'" + liveInfo.getLiveDate() + "',";
			sqlString += "'" + liveInfo.getLiveMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ס����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ס����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ס����Ϣ */
	public String DeleteLiveInfo(int liveInfoId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LiveInfo where liveInfoId=" + liveInfoId;
			db.executeUpdate(sqlString);
			result = "ס����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ס����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��ס����Ϣ */
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
	/* ����ס����Ϣ */
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
			result = "ס����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ס����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
