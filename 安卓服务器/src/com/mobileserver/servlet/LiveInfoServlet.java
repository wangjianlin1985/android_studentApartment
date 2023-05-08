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

	/*����ס����Ϣҵ������*/
	private LiveInfoDAO liveInfoDAO = new LiveInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public LiveInfoServlet() {
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
			/*��ȡ��ѯס����Ϣ�Ĳ�����Ϣ*/
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			int roomObj = 0;
			if (request.getParameter("roomObj") != null)
				roomObj = Integer.parseInt(request.getParameter("roomObj"));
			Timestamp liveDate = null;
			if (request.getParameter("liveDate") != null)
				liveDate = Timestamp.valueOf(request.getParameter("liveDate"));

			/*����ҵ���߼���ִ��ס����Ϣ��ѯ*/
			List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfo(studentObj,roomObj,liveDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ס����Ϣ����ȡס����Ϣ�������������浽�½���ס����Ϣ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = liveInfoDAO.AddLiveInfo(liveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ס����Ϣ����ȡס����Ϣ�ļ�¼���*/
			int liveInfoId = Integer.parseInt(request.getParameter("liveInfoId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = liveInfoDAO.DeleteLiveInfo(liveInfoId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ס����Ϣ֮ǰ�ȸ���liveInfoId��ѯĳ��ס����Ϣ*/
			int liveInfoId = Integer.parseInt(request.getParameter("liveInfoId"));
			LiveInfo liveInfo = liveInfoDAO.GetLiveInfo(liveInfoId);

			// �ͻ��˲�ѯ��ס����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ס����Ϣ����ȡס����Ϣ�������������浽�½���ס����Ϣ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = liveInfoDAO.UpdateLiveInfo(liveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
