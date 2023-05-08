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
import com.chengxusheji.dao.LiveInfoDAO;
import com.chengxusheji.domain.LiveInfo;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.RoomInfoDAO;
import com.chengxusheji.domain.RoomInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LiveInfoAction extends BaseAction {

    /*界面层需要查询的属性: 学生*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*界面层需要查询的属性: 所在房间*/
    private RoomInfo roomObj;
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }
    public RoomInfo getRoomObj() {
        return this.roomObj;
    }

    /*界面层需要查询的属性: 入住日期*/
    private String liveDate;
    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }
    public String getLiveDate() {
        return this.liveDate;
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

    private int liveInfoId;
    public void setLiveInfoId(int liveInfoId) {
        this.liveInfoId = liveInfoId;
    }
    public int getLiveInfoId() {
        return liveInfoId;
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
    @Resource StudentDAO studentDAO;
    @Resource RoomInfoDAO roomInfoDAO;
    @Resource LiveInfoDAO liveInfoDAO;

    /*待操作的LiveInfo对象*/
    private LiveInfo liveInfo;
    public void setLiveInfo(LiveInfo liveInfo) {
        this.liveInfo = liveInfo;
    }
    public LiveInfo getLiveInfo() {
        return this.liveInfo;
    }

    /*跳转到添加LiveInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的RoomInfo信息*/
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        return "add_view";
    }

    /*添加LiveInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddLiveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(liveInfo.getStudentObj().getStudentNumber());
            liveInfo.setStudentObj(studentObj);
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomId(liveInfo.getRoomObj().getRoomId());
            liveInfo.setRoomObj(roomObj);
            liveInfoDAO.AddLiveInfo(liveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LiveInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LiveInfo添加失败!"));
            return "error";
        }
    }

    /*查询LiveInfo信息*/
    public String QueryLiveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(liveDate == null) liveDate = "";
        List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfoInfo(studentObj, roomObj, liveDate, currentPage);
        /*计算总的页数和总的记录数*/
        liveInfoDAO.CalculateTotalPageAndRecordNumber(studentObj, roomObj, liveDate);
        /*获取到总的页码数目*/
        totalPage = liveInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = liveInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("liveInfoList",  liveInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("roomObj", roomObj);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("liveDate", liveDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLiveInfoOutputToExcel() { 
        if(liveDate == null) liveDate = "";
        List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfoInfo(studentObj,roomObj,liveDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LiveInfo信息记录"; 
        String[] headers = { "记录编号","学生","所在房间","入住日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<liveInfoList.size();i++) {
        	LiveInfo liveInfo = liveInfoList.get(i); 
        	dataset.add(new String[]{liveInfo.getLiveInfoId() + "",liveInfo.getStudentObj().getStudentName(),
liveInfo.getRoomObj().getRoomName(),
new SimpleDateFormat("yyyy-MM-dd").format(liveInfo.getLiveDate())});
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
			response.setHeader("Content-disposition","attachment; filename="+"LiveInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LiveInfo信息*/
    public String FrontQueryLiveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(liveDate == null) liveDate = "";
        List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfoInfo(studentObj, roomObj, liveDate, currentPage);
        /*计算总的页数和总的记录数*/
        liveInfoDAO.CalculateTotalPageAndRecordNumber(studentObj, roomObj, liveDate);
        /*获取到总的页码数目*/
        totalPage = liveInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = liveInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("liveInfoList",  liveInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("roomObj", roomObj);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("liveDate", liveDate);
        return "front_query_view";
    }

    /*查询要修改的LiveInfo信息*/
    public String ModifyLiveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键liveInfoId获取LiveInfo对象*/
        LiveInfo liveInfo = liveInfoDAO.GetLiveInfoByLiveInfoId(liveInfoId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("liveInfo",  liveInfo);
        return "modify_view";
    }

    /*查询要修改的LiveInfo信息*/
    public String FrontShowLiveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键liveInfoId获取LiveInfo对象*/
        LiveInfo liveInfo = liveInfoDAO.GetLiveInfoByLiveInfoId(liveInfoId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("liveInfo",  liveInfo);
        return "front_show_view";
    }

    /*更新修改LiveInfo信息*/
    public String ModifyLiveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(liveInfo.getStudentObj().getStudentNumber());
            liveInfo.setStudentObj(studentObj);
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomId(liveInfo.getRoomObj().getRoomId());
            liveInfo.setRoomObj(roomObj);
            liveInfoDAO.UpdateLiveInfo(liveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LiveInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LiveInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除LiveInfo信息*/
    public String DeleteLiveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            liveInfoDAO.DeleteLiveInfo(liveInfoId);
            ctx.put("message",  java.net.URLEncoder.encode("LiveInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LiveInfo删除失败!"));
            return "error";
        }
    }

}
