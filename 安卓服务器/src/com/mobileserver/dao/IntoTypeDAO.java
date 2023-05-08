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
	/* ������Ϣ���Ͷ��󣬽�����Ϣ���͵����ҵ�� */
	public String AddIntoType(IntoType intoType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������Ϣ���� */
			String sqlString = "insert into IntoType(infoTypeName) values (";
			sqlString += "'" + intoType.getInfoTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ϣ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ϣ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ϣ���� */
	public String DeleteIntoType(int typeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from IntoType where typeId=" + typeId;
			db.executeUpdate(sqlString);
			result = "��Ϣ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ϣ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����Ϣ���� */
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
	/* ������Ϣ���� */
	public String UpdateIntoType(IntoType intoType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update IntoType set ";
			sql += "infoTypeName='" + intoType.getInfoTypeName() + "'";
			sql += " where typeId=" + intoType.getTypeId();
			db.executeUpdate(sql);
			result = "��Ϣ���͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ϣ���͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
