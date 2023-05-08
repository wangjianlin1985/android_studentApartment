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

    /*�������Ҫ��ѯ������: ��������*/
    private String buildingName;
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    public String getBuildingName() {
        return this.buildingName;
    }

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

    private int buildingId;
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
    public int getBuildingId() {
        return buildingId;
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
    @Resource BuildingInfoDAO buildingInfoDAO;

    /*��������BuildingInfo����*/
    private BuildingInfo buildingInfo;
    public void setBuildingInfo(BuildingInfo buildingInfo) {
        this.buildingInfo = buildingInfo;
    }
    public BuildingInfo getBuildingInfo() {
        return this.buildingInfo;
    }

    /*��ת�����BuildingInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���BuildingInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddBuildingInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            buildingInfoDAO.AddBuildingInfo(buildingInfo);
            ctx.put("message",  java.net.URLEncoder.encode("BuildingInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BuildingInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯBuildingInfo��Ϣ*/
    public String QueryBuildingInfo() {
        if(currentPage == 0) currentPage = 1;
        if(buildingName == null) buildingName = "";
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfoInfo(buildingName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        buildingInfoDAO.CalculateTotalPageAndRecordNumber(buildingName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = buildingInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = buildingInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("buildingInfoList",  buildingInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("buildingName", buildingName);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryBuildingInfoOutputToExcel() { 
        if(buildingName == null) buildingName = "";
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfoInfo(buildingName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BuildingInfo��Ϣ��¼"; 
        String[] headers = { "��¼���","����У��","��������","����Ա","�����绰"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"BuildingInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯBuildingInfo��Ϣ*/
    public String FrontQueryBuildingInfo() {
        if(currentPage == 0) currentPage = 1;
        if(buildingName == null) buildingName = "";
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryBuildingInfoInfo(buildingName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        buildingInfoDAO.CalculateTotalPageAndRecordNumber(buildingName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = buildingInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = buildingInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("buildingInfoList",  buildingInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("buildingName", buildingName);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�BuildingInfo��Ϣ*/
    public String ModifyBuildingInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������buildingId��ȡBuildingInfo����*/
        BuildingInfo buildingInfo = buildingInfoDAO.GetBuildingInfoByBuildingId(buildingId);

        ctx.put("buildingInfo",  buildingInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�BuildingInfo��Ϣ*/
    public String FrontShowBuildingInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������buildingId��ȡBuildingInfo����*/
        BuildingInfo buildingInfo = buildingInfoDAO.GetBuildingInfoByBuildingId(buildingId);

        ctx.put("buildingInfo",  buildingInfo);
        return "front_show_view";
    }

    /*�����޸�BuildingInfo��Ϣ*/
    public String ModifyBuildingInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            buildingInfoDAO.UpdateBuildingInfo(buildingInfo);
            ctx.put("message",  java.net.URLEncoder.encode("BuildingInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BuildingInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��BuildingInfo��Ϣ*/
    public String DeleteBuildingInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            buildingInfoDAO.DeleteBuildingInfo(buildingId);
            ctx.put("message",  java.net.URLEncoder.encode("BuildingInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BuildingInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
