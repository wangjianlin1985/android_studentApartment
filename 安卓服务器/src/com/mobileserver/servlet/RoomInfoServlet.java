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

	/*构造房间信息业务层对象*/
	private RoomInfoDAO roomInfoDAO = new RoomInfoDAO();

	/*默认构造函数*/
	public RoomInfoServlet() {
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
			/*获取查询房间信息的参数信息*/
			int buildingObj = 0;
			if (request.getParameter("buildingObj") != null)
				buildingObj = Integer.parseInt(request.getParameter("buildingObj"));
			String roomName = request.getParameter("roomName");
			roomName = roomName == null ? "" : new String(request.getParameter(
					"roomName").getBytes("iso-8859-1"), "UTF-8");
			String roomTypeName = request.getParameter("roomTypeName");
			roomTypeName = roomTypeName == null ? "" : new String(request.getParameter(
					"roomTypeName").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行房间信息查询*/
			List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfo(buildingObj,roomName,roomTypeName);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加房间信息：获取房间信息参数，参数保存到新建的房间信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = roomInfoDAO.AddRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除房间信息：获取房间信息的记录编号*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			/*调用业务逻辑层执行删除操作*/
			String result = roomInfoDAO.DeleteRoomInfo(roomId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新房间信息之前先根据roomId查询某个房间信息*/
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			RoomInfo roomInfo = roomInfoDAO.GetRoomInfo(roomId);

			// 客户端查询的房间信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新房间信息：获取房间信息参数，参数保存到新建的房间信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = roomInfoDAO.UpdateRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
