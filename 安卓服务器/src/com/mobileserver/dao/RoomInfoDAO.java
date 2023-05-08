package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.RoomInfo;
import com.mobileserver.util.DB;

public class RoomInfoDAO {

	public List<RoomInfo> QueryRoomInfo(int buildingObj,String roomName,String roomTypeName) {
		List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();
		DB db = new DB();
		String sql = "select * from RoomInfo where 1=1";
		if (buildingObj != 0)
			sql += " and buildingObj=" + buildingObj;
		if (!roomName.equals(""))
			sql += " and roomName like '%" + roomName + "%'";
		if (!roomTypeName.equals(""))
			sql += " and roomTypeName like '%" + roomTypeName + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				RoomInfo roomInfo = new RoomInfo();
				roomInfo.setRoomId(rs.getInt("roomId"));
				roomInfo.setBuildingObj(rs.getInt("buildingObj"));
				roomInfo.setRoomName(rs.getString("roomName"));
				roomInfo.setRoomTypeName(rs.getString("roomTypeName"));
				roomInfo.setRoomPrice(rs.getFloat("roomPrice"));
				roomInfo.setTotalBedNumber(rs.getInt("totalBedNumber"));
				roomInfo.setLeftBedNum(rs.getInt("leftBedNum"));
				roomInfo.setRoomTelephone(rs.getString("roomTelephone"));
				roomInfo.setRoomMemo(rs.getString("roomMemo"));
				roomInfoList.add(roomInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomInfoList;
	}
	/* ���뷿����Ϣ���󣬽��з�����Ϣ�����ҵ�� */
	public String AddRoomInfo(RoomInfo roomInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����·�����Ϣ */
			String sqlString = "insert into RoomInfo(buildingObj,roomName,roomTypeName,roomPrice,totalBedNumber,leftBedNum,roomTelephone,roomMemo) values (";
			sqlString += roomInfo.getBuildingObj() + ",";
			sqlString += "'" + roomInfo.getRoomName() + "',";
			sqlString += "'" + roomInfo.getRoomTypeName() + "',";
			sqlString += roomInfo.getRoomPrice() + ",";
			sqlString += roomInfo.getTotalBedNumber() + ",";
			sqlString += roomInfo.getLeftBedNum() + ",";
			sqlString += "'" + roomInfo.getRoomTelephone() + "',";
			sqlString += "'" + roomInfo.getRoomMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeleteRoomInfo(int roomId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RoomInfo where roomId=" + roomId;
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��������Ϣ */
	public RoomInfo GetRoomInfo(int roomId) {
		RoomInfo roomInfo = null;
		DB db = new DB();
		String sql = "select * from RoomInfo where roomId=" + roomId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				roomInfo = new RoomInfo();
				roomInfo.setRoomId(rs.getInt("roomId"));
				roomInfo.setBuildingObj(rs.getInt("buildingObj"));
				roomInfo.setRoomName(rs.getString("roomName"));
				roomInfo.setRoomTypeName(rs.getString("roomTypeName"));
				roomInfo.setRoomPrice(rs.getFloat("roomPrice"));
				roomInfo.setTotalBedNumber(rs.getInt("totalBedNumber"));
				roomInfo.setLeftBedNum(rs.getInt("leftBedNum"));
				roomInfo.setRoomTelephone(rs.getString("roomTelephone"));
				roomInfo.setRoomMemo(rs.getString("roomMemo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomInfo;
	}
	/* ���·�����Ϣ */
	public String UpdateRoomInfo(RoomInfo roomInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RoomInfo set ";
			sql += "buildingObj=" + roomInfo.getBuildingObj() + ",";
			sql += "roomName='" + roomInfo.getRoomName() + "',";
			sql += "roomTypeName='" + roomInfo.getRoomTypeName() + "',";
			sql += "roomPrice=" + roomInfo.getRoomPrice() + ",";
			sql += "totalBedNumber=" + roomInfo.getTotalBedNumber() + ",";
			sql += "leftBedNum=" + roomInfo.getLeftBedNum() + ",";
			sql += "roomTelephone='" + roomInfo.getRoomTelephone() + "',";
			sql += "roomMemo='" + roomInfo.getRoomMemo() + "'";
			sql += " where roomId=" + roomInfo.getRoomId();
			db.executeUpdate(sql);
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
