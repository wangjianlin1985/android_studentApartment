package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.IntoTypeDAO;
import com.mobileserver.domain.IntoType;

import org.json.JSONStringer;

public class IntoTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造信息类型业务层对象*/
	private IntoTypeDAO intoTypeDAO = new IntoTypeDAO();

	/*默认构造函数*/
	public IntoTypeServlet() {
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
			/*获取查询信息类型的参数信息*/

			/*调用业务逻辑层执行信息类型查询*/
			List<IntoType> intoTypeList = intoTypeDAO.QueryIntoType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<IntoTypes>").append("\r\n");
			for (int i = 0; i < intoTypeList.size(); i++) {
				sb.append("	<IntoType>").append("\r\n")
				.append("		<typeId>")
				.append(intoTypeList.get(i).getTypeId())
				.append("</typeId>").append("\r\n")
				.append("		<infoTypeName>")
				.append(intoTypeList.get(i).getInfoTypeName())
				.append("</infoTypeName>").append("\r\n")
				.append("	</IntoType>").append("\r\n");
			}
			sb.append("</IntoTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(IntoType intoType: intoTypeList) {
				  stringer.object();
			  stringer.key("typeId").value(intoType.getTypeId());
			  stringer.key("infoTypeName").value(intoType.getInfoTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加信息类型：获取信息类型参数，参数保存到新建的信息类型对象 */ 
			IntoType intoType = new IntoType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			intoType.setTypeId(typeId);
			String infoTypeName = new String(request.getParameter("infoTypeName").getBytes("iso-8859-1"), "UTF-8");
			intoType.setInfoTypeName(infoTypeName);

			/* 调用业务层执行添加操作 */
			String result = intoTypeDAO.AddIntoType(intoType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除信息类型：获取信息类型的记录编号*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = intoTypeDAO.DeleteIntoType(typeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新信息类型之前先根据typeId查询某个信息类型*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			IntoType intoType = intoTypeDAO.GetIntoType(typeId);

			// 客户端查询的信息类型对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("typeId").value(intoType.getTypeId());
			  stringer.key("infoTypeName").value(intoType.getInfoTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新信息类型：获取信息类型参数，参数保存到新建的信息类型对象 */ 
			IntoType intoType = new IntoType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			intoType.setTypeId(typeId);
			String infoTypeName = new String(request.getParameter("infoTypeName").getBytes("iso-8859-1"), "UTF-8");
			intoType.setInfoTypeName(infoTypeName);

			/* 调用业务层执行更新操作 */
			String result = intoTypeDAO.UpdateIntoType(intoType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
