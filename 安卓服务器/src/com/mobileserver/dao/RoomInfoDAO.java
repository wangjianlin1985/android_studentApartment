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
	/* 传入房间信息对象，进行房间信息的添加业务 */
	public String AddRoomInfo(RoomInfo roomInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新房间信息 */
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
			result = "房间信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除房间信息 */
	public String DeleteRoomInfo(int roomId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RoomInfo where roomId=" + roomId;
			db.executeUpdate(sqlString);
			result = "房间信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到房间信息 */
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
	/* 更新房间信息 */
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
			result = "房间信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
