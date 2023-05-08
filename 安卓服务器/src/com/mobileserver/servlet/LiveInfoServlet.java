package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.LiveInfoDAO;
import com.mobileserver.domain.LiveInfo;

import org.json.JSONStringer;

public class LiveInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造住宿信息业务层对象*/
	private LiveInfoDAO liveInfoDAO = new LiveInfoDAO();

	/*默认构造函数*/
	public LiveInfoServlet() {
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
			/*获取查询住宿信息的参数信息*/
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			int roomObj = 0;
			if (request.getParameter("roomObj") != null)
				roomObj = Integer.parseInt(request.getParameter("roomObj"));
			Timestamp liveDate = null;
			if (request.getParameter("liveDate") != null)
				liveDate = Timestamp.valueOf(request.getParameter("liveDate"));

			/*调用业务逻辑层执行住宿信息查询*/
			List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfo(studentObj,roomObj,liveDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<LiveInfos>").append("\r\n");
			for (int i = 0; i < liveInfoList.size(); i++) {
				sb.append("	<LiveInfo>").append("\r\n")
				.append("		<liveInfoId>")
				.append(liveInfoList.get(i).getLiveInfoId())
				.append("</liveInfoId>").append("\r\n")
				.append("		<studentObj>")
				.append(liveInfoList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<roomObj>")
				.append(liveInfoList.get(i).getRoomObj())
				.append("</roomObj>").append("\r\n")
				.append("		<liveDate>")
				.append(liveInfoList.get(i).getLiveDate())
				.append("</liveDate>").append("\r\n")
				.append("		<liveMemo>")
				.append(liveInfoList.get(i).getLiveMemo())
				.append("</liveMemo>").append("\r\n")
				.append("	</LiveInfo>").append("\r\n");
			}
			sb.append("</LiveInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(LiveInfo liveInfo: liveInfoList) {
				  stringer.object();
			  stringer.key("liveInfoId").value(liveInfo.getLiveInfoId());
			  stringer.key("studentObj").value(liveInfo.getStudentObj());
			  stringer.key("roomObj").value(liveInfo.getRoomObj());
			  stringer.key("liveDate").value(liveInfo.getLiveDate());
			  stringer.key("liveMemo").value(liveInfo.getLiveMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加住宿信息：获取住宿信息参数，参数保存到新建的住宿信息对象 */ 
			LiveInfo liveInfo = new LiveInfo();
			int liveInfoId = Integer.parseInt(request.getParameter("liveInfoId"));
			liveInfo.setLiveInfoId(liveInfoId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			liveInfo.setStudentObj(studentObj);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			liveInfo.setRoomObj(roomObj);
			Timestamp liveDate = Timestamp.valueOf(request.getParameter("liveDate"));
			liveInfo.setLiveDate(liveDate);
			String liveMemo = new String(request.getParameter("liveMemo").getBytes("iso-8859-1"), "UTF-8");
			liveInfo.setLiveMemo(liveMemo);

			/* 调用业务层执行添加操作 */
			String result = liveInfoDAO.AddLiveInfo(liveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除住宿信息：获取住宿信息的记录编号*/
			int liveInfoId = Integer.parseInt(request.getParameter("liveInfoId"));
			/*调用业务逻辑层执行删除操作*/
			String result = liveInfoDAO.DeleteLiveInfo(liveInfoId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新住宿信息之前先根据liveInfoId查询某个住宿信息*/
			int liveInfoId = Integer.parseInt(request.getParameter("liveInfoId"));
			LiveInfo liveInfo = liveInfoDAO.GetLiveInfo(liveInfoId);

			// 客户端查询的住宿信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("liveInfoId").value(liveInfo.getLiveInfoId());
			  stringer.key("studentObj").value(liveInfo.getStudentObj());
			  stringer.key("roomObj").value(liveInfo.getRoomObj());
			  stringer.key("liveDate").value(liveInfo.getLiveDate());
			  stringer.key("liveMemo").value(liveInfo.getLiveMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新住宿信息：获取住宿信息参数，参数保存到新建的住宿信息对象 */ 
			LiveInfo liveInfo = new LiveInfo();
			int liveInfoId = Integer.parseInt(request.getParameter("liveInfoId"));
			liveInfo.setLiveInfoId(liveInfoId);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			liveInfo.setStudentObj(studentObj);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			liveInfo.setRoomObj(roomObj);
			Timestamp liveDate = Timestamp.valueOf(request.getParameter("liveDate"));
			liveInfo.setLiveDate(liveDate);
			String liveMemo = new String(request.getParameter("liveMemo").getBytes("iso-8859-1"), "UTF-8");
			liveInfo.setLiveMemo(liveMemo);

			/* 调用业务层执行更新操作 */
			String result = liveInfoDAO.UpdateLiveInfo(liveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
