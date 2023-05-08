package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.StudentDAO;
import com.mobileserver.domain.Student;

import org.json.JSONStringer;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ѧ����Ϣҵ������*/
	private StudentDAO studentDAO = new StudentDAO();

	/*Ĭ�Ϲ��캯��*/
	public StudentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯѧ����Ϣ�Ĳ�����Ϣ*/
			String studentNumber = request.getParameter("studentNumber");
			studentNumber = studentNumber == null ? "" : new String(request.getParameter(
					"studentNumber").getBytes("iso-8859-1"), "UTF-8");
			String studentName = request.getParameter("studentName");
			studentName = studentName == null ? "" : new String(request.getParameter(
					"studentName").getBytes("iso-8859-1"), "UTF-8");
			String classInfoId = "";
			if (request.getParameter("classInfoId") != null)
				classInfoId = request.getParameter("classInfoId");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ��ѧ����Ϣ��ѯ*/
			List<Student> studentList = studentDAO.QueryStudent(studentNumber,studentName,classInfoId,birthday,telephone);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Students>").append("\r\n");
			for (int i = 0; i < studentList.size(); i++) {
				sb.append("	<Student>").append("\r\n")
				.append("		<studentNumber>")
				.append(studentList.get(i).getStudentNumber())
				.append("</studentNumber>").append("\r\n")
				.append("		<studentName>")
				.append(studentList.get(i).getStudentName())
				.append("</studentName>").append("\r\n")
				.append("		<sex>")
				.append(studentList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<classInfoId>")
				.append(studentList.get(i).getClassInfoId())
				.append("</classInfoId>").append("\r\n")
				.append("		<birthday>")
				.append(studentList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<zzmm>")
				.append(studentList.get(i).getZzmm())
				.append("</zzmm>").append("\r\n")
				.append("		<telephone>")
				.append(studentList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<address>")
				.append(studentList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("		<studentPhoto>")
				.append(studentList.get(i).getStudentPhoto())
				.append("</studentPhoto>").append("\r\n")
				.append("	</Student>").append("\r\n");
			}
			sb.append("</Students>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Student student: studentList) {
				  stringer.object();
			  stringer.key("studentNumber").value(student.getStudentNumber());
			  stringer.key("studentName").value(student.getStudentName());
			  stringer.key("sex").value(student.getSex());
			  stringer.key("classInfoId").value(student.getClassInfoId());
			  stringer.key("birthday").value(student.getBirthday());
			  stringer.key("zzmm").value(student.getZzmm());
			  stringer.key("telephone").value(student.getTelephone());
			  stringer.key("address").value(student.getAddress());
			  stringer.key("studentPhoto").value(student.getStudentPhoto());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ѧ����Ϣ����ȡѧ����Ϣ�������������浽�½���ѧ����Ϣ���� */ 
			Student student = new Student();
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNumber(studentNumber);
			String studentName = new String(request.getParameter("studentName").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentName(studentName);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			student.setSex(sex);
			String classInfoId = new String(request.getParameter("classInfoId").getBytes("iso-8859-1"), "UTF-8");
			student.setClassInfoId(classInfoId);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			student.setBirthday(birthday);
			String zzmm = new String(request.getParameter("zzmm").getBytes("iso-8859-1"), "UTF-8");
			student.setZzmm(zzmm);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			student.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			student.setAddress(address);
			String studentPhoto = new String(request.getParameter("studentPhoto").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPhoto(studentPhoto);

			/* ����ҵ���ִ����Ӳ��� */
			String result = studentDAO.AddStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ѧ����Ϣ����ȡѧ����Ϣ��ѧ��*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = studentDAO.DeleteStudent(studentNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ѧ����Ϣ֮ǰ�ȸ���studentNumber��ѯĳ��ѧ����Ϣ*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			Student student = studentDAO.GetStudent(studentNumber);

			// �ͻ��˲�ѯ��ѧ����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("studentNumber").value(student.getStudentNumber());
			  stringer.key("studentName").value(student.getStudentName());
			  stringer.key("sex").value(student.getSex());
			  stringer.key("classInfoId").value(student.getClassInfoId());
			  stringer.key("birthday").value(student.getBirthday());
			  stringer.key("zzmm").value(student.getZzmm());
			  stringer.key("telephone").value(student.getTelephone());
			  stringer.key("address").value(student.getAddress());
			  stringer.key("studentPhoto").value(student.getStudentPhoto());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ѧ����Ϣ����ȡѧ����Ϣ�������������浽�½���ѧ����Ϣ���� */ 
			Student student = new Student();
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentNumber(studentNumber);
			String studentName = new String(request.getParameter("studentName").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentName(studentName);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			student.setSex(sex);
			String classInfoId = new String(request.getParameter("classInfoId").getBytes("iso-8859-1"), "UTF-8");
			student.setClassInfoId(classInfoId);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			student.setBirthday(birthday);
			String zzmm = new String(request.getParameter("zzmm").getBytes("iso-8859-1"), "UTF-8");
			student.setZzmm(zzmm);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			student.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			student.setAddress(address);
			String studentPhoto = new String(request.getParameter("studentPhoto").getBytes("iso-8859-1"), "UTF-8");
			student.setStudentPhoto(studentPhoto);

			/* ����ҵ���ִ�и��²��� */
			String result = studentDAO.UpdateStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
