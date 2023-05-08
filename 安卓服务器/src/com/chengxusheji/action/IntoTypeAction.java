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

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource IntoTypeDAO intoTypeDAO;

    /*待操作的IntoType对象*/
    private IntoType intoType;
    public void setIntoType(IntoType intoType) {
        this.intoType = intoType;
    }
    public IntoType getIntoType() {
        return this.intoType;
    }

    /*跳转到添加IntoType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加IntoType信息*/
    @SuppressWarnings("deprecation")
    public String AddIntoType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            intoTypeDAO.AddIntoType(intoType);
            ctx.put("message",  java.net.URLEncoder.encode("IntoType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("IntoType添加失败!"));
            return "error";
        }
    }

    /*查询IntoType信息*/
    public String QueryIntoType() {
        if(currentPage == 0) currentPage = 1;
        List<IntoType> intoTypeList = intoTypeDAO.QueryIntoTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        intoTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = intoTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = intoTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("intoTypeList",  intoTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryIntoTypeOutputToExcel() { 
        List<IntoType> intoTypeList = intoTypeDAO.QueryIntoTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "IntoType信息记录"; 
        String[] headers = { "记录编号","信息类别"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"IntoType.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询IntoType信息*/
    public String FrontQueryIntoType() {
        if(currentPage == 0) currentPage = 1;
        List<IntoType> intoTypeList = intoTypeDAO.QueryIntoTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        intoTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = intoTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = intoTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("intoTypeList",  intoTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的IntoType信息*/
    public String ModifyIntoTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取IntoType对象*/
        IntoType intoType = intoTypeDAO.GetIntoTypeByTypeId(typeId);

        ctx.put("intoType",  intoType);
        return "modify_view";
    }

    /*查询要修改的IntoType信息*/
    public String FrontShowIntoTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取IntoType对象*/
        IntoType intoType = intoTypeDAO.GetIntoTypeByTypeId(typeId);

        ctx.put("intoType",  intoType);
        return "front_show_view";
    }

    /*更新修改IntoType信息*/
    public String ModifyIntoType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            intoTypeDAO.UpdateIntoType(intoType);
            ctx.put("message",  java.net.URLEncoder.encode("IntoType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("IntoType信息更新失败!"));
            return "error";
       }
   }

    /*删除IntoType信息*/
    public String DeleteIntoType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            intoTypeDAO.DeleteIntoType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("IntoType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("IntoType删除失败!"));
            return "error";
        }
    }

}
