package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.IntoType;
import com.mobileserver.util.DB;

public class IntoTypeDAO {

	public List<IntoType> QueryIntoType() {
		List<IntoType> intoTypeList = new ArrayList<IntoType>();
		DB db = new DB();
		String sql = "select * from IntoType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				IntoType intoType = new IntoType();
				intoType.setTypeId(rs.getInt("typeId"));
				intoType.setInfoTypeName(rs.getString("infoTypeName"));
				intoTypeList.add(intoType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return intoTypeList;
	}
	/* 传入信息类型对象，进行信息类型的添加业务 */
	public String AddIntoType(IntoType intoType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新信息类型 */
			String sqlString = "insert into IntoType(infoTypeName) values (";
			sqlString += "'" + intoType.getInfoTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "信息类型添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "信息类型添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除信息类型 */
	public String DeleteIntoType(int typeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from IntoType where typeId=" + typeId;
			db.executeUpdate(sqlString);
			result = "信息类型删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "信息类型删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到信息类型 */
	public IntoType GetIntoType(int typeId) {
		IntoType intoType = null;
		DB db = new DB();
		String sql = "select * from IntoType where typeId=" + typeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				intoType = new IntoType();
				intoType.setTypeId(rs.getInt("typeId"));
				intoType.setInfoTypeName(rs.getString("infoTypeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return intoType;
	}
	/* 更新信息类型 */
	public String UpdateIntoType(IntoType intoType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update IntoType set ";
			sql += "infoTypeName='" + intoType.getInfoTypeName() + "'";
			sql += " where typeId=" + intoType.getTypeId();
			db.executeUpdate(sql);
			result = "信息类型更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "信息类型更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
