package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Student;
import com.mobileserver.util.DB;

public class StudentDAO {

	public List<Student> QueryStudent(String studentNumber,String studentName,String classInfoId,Timestamp birthday,String telephone) {
		List<Student> studentList = new ArrayList<Student>();
		DB db = new DB();
		String sql = "select * from Student where 1=1";
		if (!studentNumber.equals(""))
			sql += " and studentNumber like '%" + studentNumber + "%'";
		if (!studentName.equals(""))
			sql += " and studentName like '%" + studentName + "%'";
		if (!classInfoId.equals(""))
			sql += " and classInfoId = '" + classInfoId + "'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setStudentName(rs.getString("studentName"));
				student.setSex(rs.getString("sex"));
				student.setClassInfoId(rs.getString("classInfoId"));
				student.setBirthday(rs.getTimestamp("birthday"));
				student.setZzmm(rs.getString("zzmm"));
				student.setTelephone(rs.getString("telephone"));
				student.setAddress(rs.getString("address"));
				student.setStudentPhoto(rs.getString("studentPhoto"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return studentList;
	}
	/* ����ѧ����Ϣ���󣬽���ѧ����Ϣ�����ҵ�� */
	public String AddStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѧ����Ϣ */
			String sqlString = "insert into Student(studentNumber,studentName,sex,classInfoId,birthday,zzmm,telephone,address,studentPhoto) values (";
			sqlString += "'" + student.getStudentNumber() + "',";
			sqlString += "'" + student.getStudentName() + "',";
			sqlString += "'" + student.getSex() + "',";
			sqlString += "'" + student.getClassInfoId() + "',";
			sqlString += "'" + student.getBirthday() + "',";
			sqlString += "'" + student.getZzmm() + "',";
			sqlString += "'" + student.getTelephone() + "',";
			sqlString += "'" + student.getAddress() + "',";
			sqlString += "'" + student.getStudentPhoto() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѧ����Ϣ */
	public String DeleteStudent(String studentNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Student where studentNumber='" + studentNumber + "'";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ѧ�Ż�ȡ��ѧ����Ϣ */
	public Student GetStudent(String studentNumber) {
		Student student = null;
		DB db = new DB();
		String sql = "select * from Student where studentNumber='" + studentNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setStudentName(rs.getString("studentName"));
				student.setSex(rs.getString("sex"));
				student.setClassInfoId(rs.getString("classInfoId"));
				student.setBirthday(rs.getTimestamp("birthday"));
				student.setZzmm(rs.getString("zzmm"));
				student.setTelephone(rs.getString("telephone"));
				student.setAddress(rs.getString("address"));
				student.setStudentPhoto(rs.getString("studentPhoto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return student;
	}
	/* ����ѧ����Ϣ */
	public String UpdateStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Student set ";
			sql += "studentName='" + student.getStudentName() + "',";
			sql += "sex='" + student.getSex() + "',";
			sql += "classInfoId='" + student.getClassInfoId() + "',";
			sql += "birthday='" + student.getBirthday() + "',";
			sql += "zzmm='" + student.getZzmm() + "',";
			sql += "telephone='" + student.getTelephone() + "',";
			sql += "address='" + student.getAddress() + "',";
			sql += "studentPhoto='" + student.getStudentPhoto() + "'";
			sql += " where studentNumber='" + student.getStudentNumber() + "'";
			db.executeUpdate(sql);
			result = "ѧ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
