package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RoomInfoDAO;
import com.mobileserver.domain.RoomInfo;

import org.json.JSONStringer;

public class RoomInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���췿����Ϣҵ������*/
	private RoomInfoDAO roomInfoDAO = new RoomInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public RoomInfoServlet() {
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
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			int buildingObj = 0;
			if (request.getParameter("buildingObj") != null)
				buildingObj = Integer.parseInt(request.getParameter("buildingObj"));
			String roomName = request.getParameter("roomName");
			roomName = roomName == null ? "" : new String(request.getParameter(
					"roomName").getBytes("iso-8859-1"), "UTF-8");
			String roomTypeName = request.getParameter("roomTypeName");
			roomTypeName = roomTypeName == null ? "" : new String(request.getParameter(
					"roomTypeName").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�з�����Ϣ��ѯ*/
			List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfo(buildingObj,roomName,roomTypeName);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<RoomInfos>").append("\r\n");
			for (int i = 0; i < roomInfoList.size(); i++) {
				sb.append("	<RoomInfo>").append("\r\n")
				.append("		<roomId>")
				.append(roomInfoList.get(i).getRoomId())
				.append("</roomId>").append("\r\n")
				.append("		<buildingObj>")
				.append(roomInfoList.get(i).getBuildingObj())
				.append("</buildingObj>").append("\r\n")
				.append("		<roomName>")
				.append(roomInfoList.get(i).getRoomName())
				.append("</roomName>").append("\r\n")
				.append("		<roomTypeName>")
				.append(roomInfoList.get(i).getRoomTypeName())
				.append("</roomTypeName>").append("\r\n")
				.append("		<roomPrice>")
				.append(roomInfoList.get(i).getRoomPrice())
				.append("</roomPrice>").append("\r\n")
				.append("		<totalBedNumber>")
				.append(roomInfoList.get(i).getTotalBedNumber())
				.append("</totalBedNumber>").append("\r\n")
				.append("		<leftBedNum>")
				.append(roomInfoList.get(i).getLeftBedNum())
				.append("</leftBedNum>").append("\r\n")
				.append("		<roomTelephone>")
				.append(roomInfoList.get(i).getRoomTelephone())
				.append("</roomTelephone>").append("\r\n")
				.append("		<roomMemo>")
				.append(roomInfoList.get(i).getRoomMemo())
				.append("</roomMemo>").append("\r\n")
				.append("	</RoomInfo>").append("\r\n");
			}
			sb.append("</RoomInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(RoomInfo roomInfo: roomInfoList) {
				  stringer.object();
			  stringer.key("roomId").value(roomInfo.getRoomId());
			  stringer.key("buildingObj").value(roomInfo.getBuildingObj());
			  stringer.key("roomName").value(roomInfo.getRoomName());
			  stringer.key("roomTypeName").value(roomInfo.getRoomTypeName());
			  stringer.key("roomPrice").value(roomInfo.getRoomPrice());
			  stringer.key("totalBedNumber").value(roomInfo.getTotalBedNumber());
			  stringer.key("leftBedNum").value(roomInfo.getLeftBedNum());
			  stringer.key("roomTelephone").value(roomInfo.getRoomTelephone());
			  stringer.key("roomMemo").value(roomInfo.getRoomMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӷ�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
			RoomInfo roomInfo = new RoomInfo();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			roomInfo.setRoomId(roomId);
			int buildingObj = Integer.parseInt(request.getParameter("buildingObj"));
			roomInfo.setBuildingObj(buildingObj);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomName(roomName);
			String roomTypeName = new String(request.getParameter("roomTypeName").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomTypeName(roomTypeName);
			float roomPrice = Float.parseFloat(request.getParameter("roomPrice"));
			roomInfo.setRoomPrice(roomPrice);
			int totalBedNumber = Integer.parseInt(request.getParameter("totalBedNumber"));
			roomInfo.setTotalBedNumber(totalBedNumber);
			int leftBedNum = Integer.parseInt(request.getParameter("leftBedNum"));
			roomInfo.setLeftBedNum(leftBedNum);
			String roomTelephone = new String(request.getParameter("roomTelephone").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomTelephone(roomTelephone);
			String roomMemo = new String(request.getParameter("roomMemo").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomMemo(roomMemo);

			/* ����ҵ���ִ����Ӳ��� */
			String result = roomInfoDAO.AddRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ļ�¼���*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = roomInfoDAO.DeleteRoomInfo(roomId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���·�����Ϣ֮ǰ�ȸ���roomId��ѯĳ��������Ϣ*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			RoomInfo roomInfo = roomInfoDAO.GetRoomInfo(roomId);

			// �ͻ��˲�ѯ�ķ�����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("roomId").value(roomInfo.getRoomId());
			  stringer.key("buildingObj").value(roomInfo.getBuildingObj());
			  stringer.key("roomName").value(roomInfo.getRoomName());
			  stringer.key("roomTypeName").value(roomInfo.getRoomTypeName());
			  stringer.key("roomPrice").value(roomInfo.getRoomPrice());
			  stringer.key("totalBedNumber").value(roomInfo.getTotalBedNumber());
			  stringer.key("leftBedNum").value(roomInfo.getLeftBedNum());
			  stringer.key("roomTelephone").value(roomInfo.getRoomTelephone());
			  stringer.key("roomMemo").value(roomInfo.getRoomMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���·�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
			RoomInfo roomInfo = new RoomInfo();
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			roomInfo.setRoomId(roomId);
			int buildingObj = Integer.parseInt(request.getParameter("buildingObj"));
			roomInfo.setBuildingObj(buildingObj);
			String roomName = new String(request.getParameter("roomName").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomName(roomName);
			String roomTypeName = new String(request.getParameter("roomTypeName").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomTypeName(roomTypeName);
			float roomPrice = Float.parseFloat(request.getParameter("roomPrice"));
			roomInfo.setRoomPrice(roomPrice);
			int totalBedNumber = Integer.parseInt(request.getParameter("totalBedNumber"));
			roomInfo.setTotalBedNumber(totalBedNumber);
			int leftBedNum = Integer.parseInt(request.getParameter("leftBedNum"));
			roomInfo.setLeftBedNum(leftBedNum);
			String roomTelephone = new String(request.getParameter("roomTelephone").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomTelephone(roomTelephone);
			String roomMemo = new String(request.getParameter("roomMemo").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomMemo(roomMemo);

			/* ����ҵ���ִ�и��²��� */
			String result = roomInfoDAO.UpdateRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
