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

	/*�����ۺ���Ϣҵ������*/
	private NewsInfoDAO newsInfoDAO = new NewsInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public NewsInfoServlet() {
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
			/*��ȡ��ѯ�ۺ���Ϣ�Ĳ�����Ϣ*/
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

			/*����ҵ���߼���ִ���ۺ���Ϣ��ѯ*/
			List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfo(roomObj,infoTypeObj,infoTitle,infoDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����ۺ���Ϣ����ȡ�ۺ���Ϣ�������������浽�½����ۺ���Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = newsInfoDAO.AddNewsInfo(newsInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ۺ���Ϣ����ȡ�ۺ���Ϣ�ļ�¼���*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = newsInfoDAO.DeleteNewsInfo(newsId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����ۺ���Ϣ֮ǰ�ȸ���newsId��ѯĳ���ۺ���Ϣ*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			NewsInfo newsInfo = newsInfoDAO.GetNewsInfo(newsId);

			// �ͻ��˲�ѯ���ۺ���Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����ۺ���Ϣ����ȡ�ۺ���Ϣ�������������浽�½����ۺ���Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = newsInfoDAO.UpdateNewsInfo(newsInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
