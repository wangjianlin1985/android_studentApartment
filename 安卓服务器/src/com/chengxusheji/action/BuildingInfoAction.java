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
import com.chengxusheji.dao.BuildingInfoDAO;
import com.chengxusheji.domain.BuildingInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class BuildingInfoAction extends BaseAction {

    /*界面层需要查询的属性: 宿舍名称*/
    private String buildingName;
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    public String getBuildingName() {
        return this.buildingName;
    }

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

    private int buildingId;
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
    public int getBuildingId() {
        return buildingId;
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
    @Resource BuildingInfoDAO buildingInfoDAO;

    /*待操作的BuildingInfo对象*/
    private BuildingInfo buildingInfo;
    public void setBuildingInfo(BuildingInfo buildingInfo) {
        this.buildingInfo = buildingInfo;
    }
    public BuildingInfo getBuildingInfo() {
        return this.buildingInfo;
    }

    /*跳转到添加BuildingInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加BuildingInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddBuildingInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            buildingInfoDAO.AddBuildingInfo(buildingInfo);
            ctx.put("message",  java.net.URLEncoder.encode("BuildingInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BuildingInfo添加失败!"));
            return "error";
        }
    }

    /*查询BuildingInfo信息*/
    public String QueryBuildingInfo() {
        if(currentPage == 0) currentPage = 1;
        if(buildingName == null) buildingName = "";
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfoInfo(buildingName, currentPage);
        /*计算总的页数和总的记录数*/
        buildingInfoDAO.CalculateTotalPageAndRecordNumber(buildingName);
        /*获取到总的页码数目*/
        totalPage = buildingInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = buildingInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("buildingInfoList",  buildingInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("buildingName", buildingName);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryBuildingInfoOutputToExcel() { 
        if(buildingName == null) buildingName = "";
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfoInfo(buildingName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BuildingInfo信息记录"; 
        String[] headers = { "记录编号","所在校区","宿舍名称","管理员","门卫电话"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<buildingInfoList.size();i++) {
        	BuildingInfo buildingInfo = buildingInfoList.get(i); 
        	dataset.add(new String[]{buildingInfo.getBuildingId() + "",buildingInfo.getAreaObj(),buildingInfo.getBuildingName(),buildingInfo.getManageMan(),buildingInfo.getTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"BuildingInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询BuildingInfo信息*/
    public String FrontQueryBuildingInfo() {
        if(currentPage == 0) currentPage = 1;
        if(buildingName == null) buildingName = "";
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfoInfo(buildingName, currentPage);
        /*计算总的页数和总的记录数*/
        buildingInfoDAO.CalculateTotalPageAndRecordNumber(buildingName);
        /*获取到总的页码数目*/
        totalPage = buildingInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = buildingInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("buildingInfoList",  buildingInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("buildingName", buildingName);
        return "front_query_view";
    }

    /*查询要修改的BuildingInfo信息*/
    public String ModifyBuildingInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键buildingId获取BuildingInfo对象*/
        BuildingInfo buildingInfo = buildingInfoDAO.GetBuildingInfoByBuildingId(buildingId);

        ctx.put("buildingInfo",  buildingInfo);
        return "modify_view";
    }

    /*查询要修改的BuildingInfo信息*/
    public String FrontShowBuildingInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键buildingId获取BuildingInfo对象*/
        BuildingInfo buildingInfo = buildingInfoDAO.GetBuildingInfoByBuildingId(buildingId);

        ctx.put("buildingInfo",  buildingInfo);
        return "front_show_view";
    }

    /*更新修改BuildingInfo信息*/
    public String ModifyBuildingInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            buildingInfoDAO.UpdateBuildingInfo(buildingInfo);
            ctx.put("message",  java.net.URLEncoder.encode("BuildingInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BuildingInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除BuildingInfo信息*/
    public String DeleteBuildingInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            buildingInfoDAO.DeleteBuildingInfo(buildingId);
            ctx.put("message",  java.net.URLEncoder.encode("BuildingInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BuildingInfo删除失败!"));
            return "error";
        }
    }

}
