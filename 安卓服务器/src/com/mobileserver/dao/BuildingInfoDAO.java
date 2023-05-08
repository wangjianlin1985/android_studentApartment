package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.BuildingInfo;
import com.mobileserver.util.DB;

public class BuildingInfoDAO {

	public List<BuildingInfo> QueryBuildingInfo(String buildingName) {
		List<BuildingInfo> buildingInfoList = new ArrayList<BuildingInfo>();
		DB db = new DB();
		String sql = "select * from BuildingInfo where 1=1";
		if (!buildingName.equals(""))
			sql += " and buildingName like '%" + buildingName + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				BuildingInfo buildingInfo = new BuildingInfo();
				buildingInfo.setBuildingId(rs.getInt("buildingId"));
				buildingInfo.setAreaObj(rs.getString("areaObj"));
				buildingInfo.setBuildingName(rs.getString("buildingName"));
				buildingInfo.setManageMan(rs.getString("manageMan"));
				buildingInfo.setTelephone(rs.getString("telephone"));
				buildingInfoList.add(buildingInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return buildingInfoList;
	}
	/* 传入宿舍信息对象，进行宿舍信息的添加业务 */
	public String AddBuildingInfo(BuildingInfo buildingInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新宿舍信息 */
			String sqlString = "insert into BuildingInfo(areaObj,buildingName,manageMan,telephone) values (";
			sqlString += "'" + buildingInfo.getAreaObj() + "',";
			sqlString += "'" + buildingInfo.getBuildingName() + "',";
			sqlString += "'" + buildingInfo.getManageMan() + "',";
			sqlString += "'" + buildingInfo.getTelephone() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "宿舍信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "宿舍信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除宿舍信息 */
	public String DeleteBuildingInfo(int buildingId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from BuildingInfo where buildingId=" + buildingId;
			db.executeUpdate(sqlString);
			result = "宿舍信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "宿舍信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到宿舍信息 */
	public BuildingInfo GetBuildingInfo(int buildingId) {
		BuildingInfo buildingInfo = null;
		DB db = new DB();
		String sql = "select * from BuildingInfo where buildingId=" + buildingId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				buildingInfo = new BuildingInfo();
				buildingInfo.setBuildingId(rs.getInt("buildingId"));
				buildingInfo.setAreaObj(rs.getString("areaObj"));
				buildingInfo.setBuildingName(rs.getString("buildingName"));
				buildingInfo.setManageMan(rs.getString("manageMan"));
				buildingInfo.setTelephone(rs.getString("telephone"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return buildingInfo;
	}
	/* 更新宿舍信息 */
	public String UpdateBuildingInfo(BuildingInfo buildingInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update BuildingInfo set ";
			sql += "areaObj='" + buildingInfo.getAreaObj() + "',";
			sql += "buildingName='" + buildingInfo.getBuildingName() + "',";
			sql += "manageMan='" + buildingInfo.getManageMan() + "',";
			sql += "telephone='" + buildingInfo.getTelephone() + "'";
			sql += " where buildingId=" + buildingInfo.getBuildingId();
			db.executeUpdate(sql);
			result = "宿舍信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "宿舍信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
