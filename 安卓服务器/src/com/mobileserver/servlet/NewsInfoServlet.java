package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NewsInfoDAO;
import com.mobileserver.domain.NewsInfo;

import org.json.JSONStringer;

public class NewsInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造综合信息业务层对象*/
	private NewsInfoDAO newsInfoDAO = new NewsInfoDAO();

	/*默认构造函数*/
	public NewsInfoServlet() {
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
			/*获取查询综合信息的参数信息*/
			int roomObj = 0;
			if (request.getParameter("roomObj") != null)
				roomObj = Integer.parseInt(request.getParameter("roomObj"));
			int infoTypeObj = 0;
			if (request.getParameter("infoTypeObj") != null)
				infoTypeObj = Integer.parseInt(request.getParameter("infoTypeObj"));
			String infoTitle = request.getParameter("infoTitle");
			infoTitle = infoTitle == null ? "" : new String(request.getParameter(
					"infoTitle").getBytes("iso-8859-1"), "UTF-8");
			Timestamp infoDate = null;
			if (request.getParameter("infoDate") != null)
				infoDate = Timestamp.valueOf(request.getParameter("infoDate"));

			/*调用业务逻辑层执行综合信息查询*/
			List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfo(roomObj,infoTypeObj,infoTitle,infoDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<NewsInfos>").append("\r\n");
			for (int i = 0; i < newsInfoList.size(); i++) {
				sb.append("	<NewsInfo>").append("\r\n")
				.append("		<newsId>")
				.append(newsInfoList.get(i).getNewsId())
				.append("</newsId>").append("\r\n")
				.append("		<roomObj>")
				.append(newsInfoList.get(i).getRoomObj())
				.append("</roomObj>").append("\r\n")
				.append("		<infoTypeObj>")
				.append(newsInfoList.get(i).getInfoTypeObj())
				.append("</infoTypeObj>").append("\r\n")
				.append("		<infoTitle>")
				.append(newsInfoList.get(i).getInfoTitle())
				.append("</infoTitle>").append("\r\n")
				.append("		<infoContent>")
				.append(newsInfoList.get(i).getInfoContent())
				.append("</infoContent>").append("\r\n")
				.append("		<infoDate>")
				.append(newsInfoList.get(i).getInfoDate())
				.append("</infoDate>").append("\r\n")
				.append("	</NewsInfo>").append("\r\n");
			}
			sb.append("</NewsInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(NewsInfo newsInfo: newsInfoList) {
				  stringer.object();
			  stringer.key("newsId").value(newsInfo.getNewsId());
			  stringer.key("roomObj").value(newsInfo.getRoomObj());
			  stringer.key("infoTypeObj").value(newsInfo.getInfoTypeObj());
			  stringer.key("infoTitle").value(newsInfo.getInfoTitle());
			  stringer.key("infoContent").value(newsInfo.getInfoContent());
			  stringer.key("infoDate").value(newsInfo.getInfoDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加综合信息：获取综合信息参数，参数保存到新建的综合信息对象 */ 
			NewsInfo newsInfo = new NewsInfo();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			newsInfo.setNewsId(newsId);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			newsInfo.setRoomObj(roomObj);
			int infoTypeObj = Integer.parseInt(request.getParameter("infoTypeObj"));
			newsInfo.setInfoTypeObj(infoTypeObj);
			String infoTitle = new String(request.getParameter("infoTitle").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setInfoTitle(infoTitle);
			String infoContent = new String(request.getParameter("infoContent").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setInfoContent(infoContent);
			Timestamp infoDate = Timestamp.valueOf(request.getParameter("infoDate"));
			newsInfo.setInfoDate(infoDate);

			/* 调用业务层执行添加操作 */
			String result = newsInfoDAO.AddNewsInfo(newsInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除综合信息：获取综合信息的记录编号*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			/*调用业务逻辑层执行删除操作*/
			String result = newsInfoDAO.DeleteNewsInfo(newsId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新综合信息之前先根据newsId查询某个综合信息*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			NewsInfo newsInfo = newsInfoDAO.GetNewsInfo(newsId);

			// 客户端查询的综合信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("newsId").value(newsInfo.getNewsId());
			  stringer.key("roomObj").value(newsInfo.getRoomObj());
			  stringer.key("infoTypeObj").value(newsInfo.getInfoTypeObj());
			  stringer.key("infoTitle").value(newsInfo.getInfoTitle());
			  stringer.key("infoContent").value(newsInfo.getInfoContent());
			  stringer.key("infoDate").value(newsInfo.getInfoDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新综合信息：获取综合信息参数，参数保存到新建的综合信息对象 */ 
			NewsInfo newsInfo = new NewsInfo();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			newsInfo.setNewsId(newsId);
			int roomObj = Integer.parseInt(request.getParameter("roomObj"));
			newsInfo.setRoomObj(roomObj);
			int infoTypeObj = Integer.parseInt(request.getParameter("infoTypeObj"));
			newsInfo.setInfoTypeObj(infoTypeObj);
			String infoTitle = new String(request.getParameter("infoTitle").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setInfoTitle(infoTitle);
			String infoContent = new String(request.getParameter("infoContent").getBytes("iso-8859-1"), "UTF-8");
			newsInfo.setInfoContent(infoContent);
			Timestamp infoDate = Timestamp.valueOf(request.getParameter("infoDate"));
			newsInfo.setInfoDate(infoDate);

			/* 调用业务层执行更新操作 */
			String result = newsInfoDAO.UpdateNewsInfo(newsInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
