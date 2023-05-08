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

    /*界面层需要查询的属性: 所在宿舍*/
    private BuildingInfo buildingObj;
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }
    public BuildingInfo getBuildingObj() {
        return this.buildingObj;
    }

    /*界面层需要查询的属性: 房间名称*/
    private String roomName;
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomName() {
        return this.roomName;
    }

    /*界面层需要查询的属性: 房间类型*/
    private String roomTypeName;
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
    public String getRoomTypeName() {
        return this.roomTypeName;
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

    private int roomId;
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int getRoomId() {
        return roomId;
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
    @Resource RoomInfoDAO roomInfoDAO;

    /*待操作的RoomInfo对象*/
    private RoomInfo roomInfo;
    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }
    public RoomInfo getRoomInfo() {
        return this.roomInfo;
    }

    /*跳转到添加RoomInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的BuildingInfo信息*/
        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        return "add_view";
    }

    /*添加RoomInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BuildingInfo buildingObj = buildingInfoDAO.GetBuildingInfoByBuildingId(roomInfo.getBuildingObj().getBuildingId());
            roomInfo.setBuildingObj(buildingObj);
            roomInfoDAO.AddRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo添加失败!"));
            return "error";
        }
    }

    /*查询RoomInfo信息*/
    public String QueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomName == null) roomName = "";
        if(roomTypeName == null) roomTypeName = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(buildingObj, roomName, roomTypeName, currentPage);
        /*计算总的页数和总的记录数*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(buildingObj, roomName, roomTypeName);
        /*获取到总的页码数目*/
        totalPage = roomInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryRoomInfoOutputToExcel() { 
        if(roomName == null) roomName = "";
        if(roomTypeName == null) roomTypeName = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(buildingObj,roomName,roomTypeName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RoomInfo信息记录"; 
        String[] headers = { "记录编号","所在宿舍","房间名称","房间类型","房间价格(元/月)","总床位","剩余床位","寝室电话"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"RoomInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询RoomInfo信息*/
    public String FrontQueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomName == null) roomName = "";
        if(roomTypeName == null) roomTypeName = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(buildingObj, roomName, roomTypeName, currentPage);
        /*计算总的页数和总的记录数*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(buildingObj, roomName, roomTypeName);
        /*获取到总的页码数目*/
        totalPage = roomInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的RoomInfo信息*/
    public String ModifyRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomId获取RoomInfo对象*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomId(roomId);

        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("roomInfo",  roomInfo);
        return "modify_view";
    }

    /*查询要修改的RoomInfo信息*/
    public String FrontShowRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomId获取RoomInfo对象*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomId(roomId);

        List<BuildingInfo> buildingInfoList = buildingInfoDAO.QueryAllBuildingInfoInfo();
        ctx.put("buildingInfoList", buildingInfoList);
        ctx.put("roomInfo",  roomInfo);
        return "front_show_view";
    }

    /*更新修改RoomInfo信息*/
    public String ModifyRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BuildingInfo buildingObj = buildingInfoDAO.GetBuildingInfoByBuildingId(roomInfo.getBuildingObj().getBuildingId());
            roomInfo.setBuildingObj(buildingObj);
            roomInfoDAO.UpdateRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除RoomInfo信息*/
    public String DeleteRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomInfoDAO.DeleteRoomInfo(roomId);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo删除失败!"));
            return "error";
        }
    }

}
