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

	/*������Ϣ����ҵ������*/
	private IntoTypeDAO intoTypeDAO = new IntoTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public IntoTypeServlet() {
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
			/*��ȡ��ѯ��Ϣ���͵Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ����Ϣ���Ͳ�ѯ*/
			List<IntoType> intoTypeList = intoTypeDAO.QueryIntoType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����Ϣ���ͣ���ȡ��Ϣ���Ͳ������������浽�½�����Ϣ���Ͷ��� */ 
			IntoType intoType = new IntoType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			intoType.setTypeId(typeId);
			String infoTypeName = new String(request.getParameter("infoTypeName").getBytes("iso-8859-1"), "UTF-8");
			intoType.setInfoTypeName(infoTypeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = intoTypeDAO.AddIntoType(intoType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����Ϣ���ͣ���ȡ��Ϣ���͵ļ�¼���*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = intoTypeDAO.DeleteIntoType(typeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������Ϣ����֮ǰ�ȸ���typeId��ѯĳ����Ϣ����*/
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			IntoType intoType = intoTypeDAO.GetIntoType(typeId);

			// �ͻ��˲�ѯ����Ϣ���Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������Ϣ���ͣ���ȡ��Ϣ���Ͳ������������浽�½�����Ϣ���Ͷ��� */ 
			IntoType intoType = new IntoType();
			int typeId = Integer.parseInt(request.getParameter("typeId"));
			intoType.setTypeId(typeId);
			String infoTypeName = new String(request.getParameter("infoTypeName").getBytes("iso-8859-1"), "UTF-8");
			intoType.setInfoTypeName(infoTypeName);

			/* ����ҵ���ִ�и��²��� */
			String result = intoTypeDAO.UpdateIntoType(intoType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
