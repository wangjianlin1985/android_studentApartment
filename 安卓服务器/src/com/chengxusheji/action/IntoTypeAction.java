package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.IntoTypeDAO;
import com.chengxusheji.domain.IntoType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class IntoTypeAction extends BaseAction {

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int typeId;
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource IntoTypeDAO intoTypeDAO;

    /*��������IntoType����*/
    private IntoType intoType;
    public void setIntoType(IntoType intoType) {
        this.intoType = intoType;
    }
    public IntoType getIntoType() {
        return this.intoType;
    }

    /*��ת�����IntoType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���IntoType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddIntoType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            intoTypeDAO.AddIntoType(intoType);
            ctx.put("message",  java.net.URLEncoder.encode("IntoType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("IntoType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯIntoType��Ϣ*/
    public String QueryIntoType() {
        if(currentPage == 0) currentPage = 1;
        List<IntoType> intoTypeList = intoTypeDAO.QueryIntoTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        intoTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = intoTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = intoTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("intoTypeList",  intoTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryIntoTypeOutputToExcel() { 
        List<IntoType> intoTypeList = intoTypeDAO.QueryIntoTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "IntoType��Ϣ��¼"; 
        String[] headers = { "��¼���","��Ϣ���"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<intoTypeList.size();i++) {
        	IntoType intoType = intoTypeList.get(i); 
        	dataset.add(new String[]{intoType.getTypeId() + "",intoType.getInfoTypeName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"IntoType.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯIntoType��Ϣ*/
    public String FrontQueryIntoType() {
        if(currentPage == 0) currentPage = 1;
        List<IntoType> intoTypeList = intoTypeDAO.QueryIntoTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        intoTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = intoTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = intoTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("intoTypeList",  intoTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�IntoType��Ϣ*/
    public String ModifyIntoTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡIntoType����*/
        IntoType intoType = intoTypeDAO.GetIntoTypeByTypeId(typeId);

        ctx.put("intoType",  intoType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�IntoType��Ϣ*/
    public String FrontShowIntoTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡIntoType����*/
        IntoType intoType = intoTypeDAO.GetIntoTypeByTypeId(typeId);

        ctx.put("intoType",  intoType);
        return "front_show_view";
    }

    /*�����޸�IntoType��Ϣ*/
    public String ModifyIntoType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            intoTypeDAO.UpdateIntoType(intoType);
            ctx.put("message",  java.net.URLEncoder.encode("IntoType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("IntoType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��IntoType��Ϣ*/
    public String DeleteIntoType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            intoTypeDAO.DeleteIntoType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("IntoTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("IntoTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
