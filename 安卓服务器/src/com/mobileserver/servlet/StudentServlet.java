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

	/*构造学生信息业务层对象*/
	private StudentDAO studentDAO = new StudentDAO();

	/*默认构造函数*/
	public StudentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询学生信息的参数信息*/
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

			/*调用业务逻辑层执行学生信息查询*/
			List<Student> studentList = studentDAO.QueryStudent(studentNumber,studentName,classInfoId,birthday,telephone);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加学生信息：获取学生信息参数，参数保存到新建的学生信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = studentDAO.AddStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除学生信息：获取学生信息的学号*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = studentDAO.DeleteStudent(studentNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新学生信息之前先根据studentNumber查询某个学生信息*/
			String studentNumber = new String(request.getParameter("studentNumber").getBytes("iso-8859-1"), "UTF-8");
			Student student = studentDAO.GetStudent(studentNumber);

			// 客户端查询的学生信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新学生信息：获取学生信息参数，参数保存到新建的学生信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = studentDAO.UpdateStudent(student);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
