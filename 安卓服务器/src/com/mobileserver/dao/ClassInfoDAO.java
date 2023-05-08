package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.ClassInfo;
import com.mobileserver.util.DB;

public class ClassInfoDAO {

	public List<ClassInfo> QueryClassInfo(String classNo,String className,String banzhuren,Timestamp beginDate) {
		List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
		DB db = new DB();
		String sql = "select * from ClassInfo where 1=1";
		if (!classNo.equals(""))
			sql += " and classNo like '%" + classNo + "%'";
		if (!className.equals(""))
			sql += " and className like '%" + className + "%'";
		if (!banzhuren.equals(""))
			sql += " and banzhuren like '%" + banzhuren + "%'";
		if(beginDate!=null)
			sql += " and beginDate='" + beginDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ClassInfo classInfo = new ClassInfo();
				classInfo.setClassNo(rs.getString("classNo"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setBanzhuren(rs.getString("banzhuren"));
				classInfo.setBeginDate(rs.getTimestamp("beginDate"));
				classInfoList.add(classInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfoList;
	}
	/* ����༶��Ϣ���󣬽��а༶��Ϣ�����ҵ�� */
	public String AddClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����°༶��Ϣ */
			String sqlString = "insert into ClassInfo(classNo,className,banzhuren,beginDate) values (";
			sqlString += "'" + classInfo.getClassNo() + "',";
			sqlString += "'" + classInfo.getClassName() + "',";
			sqlString += "'" + classInfo.getBanzhuren() + "',";
			sqlString += "'" + classInfo.getBeginDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�༶��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���༶��Ϣ */
	public String DeleteClassInfo(String classNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from ClassInfo where classNo='" + classNo + "'";
			db.executeUpdate(sqlString);
			result = "�༶��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݰ༶��Ż�ȡ���༶��Ϣ */
	public ClassInfo GetClassInfo(String classNo) {
		ClassInfo classInfo = null;
		DB db = new DB();
		String sql = "select * from ClassInfo where classNo='" + classNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				classInfo = new ClassInfo();
				classInfo.setClassNo(rs.getString("classNo"));
				classInfo.setClassName(rs.getString("className"));
				classInfo.setBanzhuren(rs.getString("banzhuren"));
				classInfo.setBeginDate(rs.getTimestamp("beginDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return classInfo;
	}
	/* ���°༶��Ϣ */
	public String UpdateClassInfo(ClassInfo classInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update ClassInfo set ";
			sql += "className='" + classInfo.getClassName() + "',";
			sql += "banzhuren='" + classInfo.getBanzhuren() + "',";
			sql += "beginDate='" + classInfo.getBeginDate() + "'";
			sql += " where classNo='" + classInfo.getClassNo() + "'";
			db.executeUpdate(sql);
			result = "�༶��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�༶��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
