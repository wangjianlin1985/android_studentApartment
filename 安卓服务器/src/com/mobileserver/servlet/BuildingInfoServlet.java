package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.BuildingInfoDAO;
import com.mobileserver.domain.BuildingInfo;

import org.json.JSONStringer;

public class BuildingInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造宿舍信息业务层对象*/
	private BuildingInfoDAO buildingInfoDAO = new BuildingInfoDAO();

	/*默认构造函数*/
	public BuildingInfoServlet() {
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
			/*获取查询宿舍信息的参数信息*/
			String buildingName = request.getParameter("buildingName");
			buildingName = buildingName == null ? "" : new String(request.getParameter(
					"buildingName").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行宿舍信息查询*/
			List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfo(buildingName);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<BuildingInfos>").append("\r\n");
			for (int i = 0; i < buildingInfoList.size(); i++) {
				sb.append("	<BuildingInfo>").append("\r\n")
				.append("		<buildingId>")
				.append(buildingInfoList.get(i).getBuildingId())
				.append("</buildingId>").append("\r\n")
				.append("		<areaObj>")
				.append(buildingInfoList.get(i).getAreaObj())
				.append("</areaObj>").append("\r\n")
				.append("		<buildingName>")
				.append(buildingInfoList.get(i).getBuildingName())
				.append("</buildingName>").append("\r\n")
				.append("		<manageMan>")
				.append(buildingInfoList.get(i).getManageMan())
				.append("</manageMan>").append("\r\n")
				.append("		<telephone>")
				.append(buildingInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("	</BuildingInfo>").append("\r\n");
			}
			sb.append("</BuildingInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(BuildingInfo buildingInfo: buildingInfoList) {
				  stringer.object();
			  stringer.key("buildingId").value(buildingInfo.getBuildingId());
			  stringer.key("areaObj").value(buildingInfo.getAreaObj());
			  stringer.key("buildingName").value(buildingInfo.getBuildingName());
			  stringer.key("manageMan").value(buildingInfo.getManageMan());
			  stringer.key("telephone").value(buildingInfo.getTelephone());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加宿舍信息：获取宿舍信息参数，参数保存到新建的宿舍信息对象 */ 
			BuildingInfo buildingInfo = new BuildingInfo();
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			buildingInfo.setBuildingId(buildingId);
			String areaObj = new String(request.getParameter("areaObj").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setAreaObj(areaObj);
			String buildingName = new String(request.getParameter("buildingName").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setBuildingName(buildingName);
			String manageMan = new String(request.getParameter("manageMan").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setManageMan(manageMan);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setTelephone(telephone);

			/* 调用业务层执行添加操作 */
			String result = buildingInfoDAO.AddBuildingInfo(buildingInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除宿舍信息：获取宿舍信息的记录编号*/
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			/*调用业务逻辑层执行删除操作*/
			String result = buildingInfoDAO.DeleteBuildingInfo(buildingId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新宿舍信息之前先根据buildingId查询某个宿舍信息*/
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			BuildingInfo buildingInfo = buildingInfoDAO.GetBuildingInfo(buildingId);

			// 客户端查询的宿舍信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("buildingId").value(buildingInfo.getBuildingId());
			  stringer.key("areaObj").value(buildingInfo.getAreaObj());
			  stringer.key("buildingName").value(buildingInfo.getBuildingName());
			  stringer.key("manageMan").value(buildingInfo.getManageMan());
			  stringer.key("telephone").value(buildingInfo.getTelephone());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新宿舍信息：获取宿舍信息参数，参数保存到新建的宿舍信息对象 */ 
			BuildingInfo buildingInfo = new BuildingInfo();
			int buildingId = Integer.parseInt(request.getParameter("buildingId"));
			buildingInfo.setBuildingId(buildingId);
			String areaObj = new String(request.getParameter("areaObj").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setAreaObj(areaObj);
			String buildingName = new String(request.getParameter("buildingName").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setBuildingName(buildingName);
			String manageMan = new String(request.getParameter("manageMan").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setManageMan(manageMan);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			buildingInfo.setTelephone(telephone);

			/* 调用业务层执行更新操作 */
			String result = buildingInfoDAO.UpdateBuildingInfo(buildingInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
