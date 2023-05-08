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
import com.chengxusheji.dao.RoomInfoDAO;
import com.chengxusheji.domain.RoomInfo;
import com.chengxusheji.dao.BuildingInfoDAO;
import com.chengxusheji.domain.BuildingInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RoomInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: ��������*/
    private BuildingInfo buildingObj;
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }
    public BuildingInfo getBuildingObj() {
        return this.buildingObj;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String roomName;
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomName() {
        return this.roomName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String roomTypeName;
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
    public String getRoomTypeName() {
        return this.roomTypeName;
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

    private int roomId;
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int getRoomId() {
        return roomId;
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
    @Resource RoomInfoDAO roomInfoDAO;

    /*��������RoomInfo����*/
    private RoomInfo roomInfo;
    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }
    public RoomInfo getRoomInfo() {
        return this.roomInfo;
    }

    /*��ת�����RoomInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�BuildingInfo��Ϣ*/
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        return "add_view";
    }

    /*���RoomInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BuildingInfo buildingObj = buildingInfoDAO.GetBuildingInfoByBuildingId(roomInfo.getBuildingObj().getBuildingId());
            roomInfo.setBuildingObj(buildingObj);
            roomInfoDAO.AddRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯRoomInfo��Ϣ*/
    public String QueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomName == null) roomName = "";
        if(roomTypeName == null) roomTypeName = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(buildingObj, roomName, roomTypeName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(buildingObj, roomName, roomTypeName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = roomInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomInfoList",  roomInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("buildingObj", buildingObj);
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("roomName", roomName);
        ctx.put("roomTypeName", roomTypeName);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryRoomInfoOutputToExcel() { 
        if(roomName == null) roomName = "";
        if(roomTypeName == null) roomTypeName = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(buildingObj,roomName,roomTypeName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RoomInfo��Ϣ��¼"; 
        String[] headers = { "��¼���","��������","��������","��������","����۸�(Ԫ/��)","�ܴ�λ","ʣ�ലλ","���ҵ绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<roomInfoList.size();i++) {
        	RoomInfo roomInfo = roomInfoList.get(i); 
        	dataset.add(new String[]{roomInfo.getRoomId() + "",roomInfo.getBuildingObj().getBuildingName(),
roomInfo.getRoomName(),roomInfo.getRoomTypeName(),roomInfo.getRoomPrice() + "",roomInfo.getTotalBedNumber() + "",roomInfo.getLeftBedNum() + "",roomInfo.getRoomTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"RoomInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯRoomInfo��Ϣ*/
    public String FrontQueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomName == null) roomName = "";
        if(roomTypeName == null) roomTypeName = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(buildingObj, roomName, roomTypeName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(buildingObj, roomName, roomTypeName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = roomInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomInfoList",  roomInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("buildingObj", buildingObj);
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("roomName", roomName);
        ctx.put("roomTypeName", roomTypeName);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�RoomInfo��Ϣ*/
    public String ModifyRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomId��ȡRoomInfo����*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomId(roomId);

        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("roomInfo",  roomInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�RoomInfo��Ϣ*/
    public String FrontShowRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomId��ȡRoomInfo����*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomId(roomId);

        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("roomInfo",  roomInfo);
        return "front_show_view";
    }

    /*�����޸�RoomInfo��Ϣ*/
    public String ModifyRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BuildingInfo buildingObj = buildingInfoDAO.GetBuildingInfoByBuildingId(roomInfo.getBuildingObj().getBuildingId());
            roomInfo.setBuildingObj(buildingObj);
            roomInfoDAO.UpdateRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��RoomInfo��Ϣ*/
    public String DeleteRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomInfoDAO.DeleteRoomInfo(roomId);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
