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

    /*�������Ҫ��ѯ������: ѧ��*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*�������Ҫ��ѯ������: ���ڷ���*/
    private RoomInfo roomObj;
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }
    public RoomInfo getRoomObj() {
        return this.roomObj;
    }

    /*�������Ҫ��ѯ������: ��ס����*/
    private String liveDate;
    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }
    public String getLiveDate() {
        return this.liveDate;
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

    private int liveInfoId;
    public void setLiveInfoId(int liveInfoId) {
        this.liveInfoId = liveInfoId;
    }
    public int getLiveInfoId() {
        return liveInfoId;
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
    @Resource StudentDAO studentDAO;
    @Resource RoomInfoDAO roomInfoDAO;
    @Resource LiveInfoDAO liveInfoDAO;

    /*��������LiveInfo����*/
    private LiveInfo liveInfo;
    public void setLiveInfo(LiveInfo liveInfo) {
        this.liveInfo = liveInfo;
    }
    public LiveInfo getLiveInfo() {
        return this.liveInfo;
    }

    /*��ת�����LiveInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�RoomInfo��Ϣ*/
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        return "add_view";
    }

    /*���LiveInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLiveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(liveInfo.getStudentObj().getStudentNumber());
            liveInfo.setStudentObj(studentObj);
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomId(liveInfo.getRoomObj().getRoomId());
            liveInfo.setRoomObj(roomObj);
            liveInfoDAO.AddLiveInfo(liveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LiveInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LiveInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLiveInfo��Ϣ*/
    public String QueryLiveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(liveDate == null) liveDate = "";
        List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfoInfo(studentObj, roomObj, liveDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        liveInfoDAO.CalculateTotalPageAndRecordNumber(studentObj, roomObj, liveDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = liveInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryLiveInfoOutputToExcel() { 
        if(liveDate == null) liveDate = "";
        List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfoInfo(studentObj,roomObj,liveDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LiveInfo��Ϣ��¼"; 
        String[] headers = { "��¼���","ѧ��","���ڷ���","��ס����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LiveInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLiveInfo��Ϣ*/
    public String FrontQueryLiveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(liveDate == null) liveDate = "";
        List<LiveInfo> liveInfoList = liveInfoDAO.QueryLiveInfoInfo(studentObj, roomObj, liveDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        liveInfoDAO.CalculateTotalPageAndRecordNumber(studentObj, roomObj, liveDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = liveInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�LiveInfo��Ϣ*/
    public String ModifyLiveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������liveInfoId��ȡLiveInfo����*/
        LiveInfo liveInfo = liveInfoDAO.GetLiveInfoByLiveInfoId(liveInfoId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("liveInfo",  liveInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LiveInfo��Ϣ*/
    public String FrontShowLiveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������liveInfoId��ȡLiveInfo����*/
        LiveInfo liveInfo = liveInfoDAO.GetLiveInfoByLiveInfoId(liveInfoId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("liveInfo",  liveInfo);
        return "front_show_view";
    }

    /*�����޸�LiveInfo��Ϣ*/
    public String ModifyLiveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(liveInfo.getStudentObj().getStudentNumber());
            liveInfo.setStudentObj(studentObj);
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomId(liveInfo.getRoomObj().getRoomId());
            liveInfo.setRoomObj(roomObj);
            liveInfoDAO.UpdateLiveInfo(liveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LiveInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LiveInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LiveInfo��Ϣ*/
    public String DeleteLiveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            liveInfoDAO.DeleteLiveInfo(liveInfoId);
            ctx.put("message",  java.net.URLEncoder.encode("LiveInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LiveInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
