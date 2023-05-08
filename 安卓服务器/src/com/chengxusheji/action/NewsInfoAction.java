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
import com.chengxusheji.dao.NewsInfoDAO;
import com.chengxusheji.domain.NewsInfo;
import com.chengxusheji.dao.RoomInfoDAO;
import com.chengxusheji.domain.RoomInfo;
import com.chengxusheji.dao.IntoTypeDAO;
import com.chengxusheji.domain.IntoType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NewsInfoAction extends BaseAction {

    /*界面层需要查询的属性: 寝室房间*/
    private RoomInfo roomObj;
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }
    public RoomInfo getRoomObj() {
        return this.roomObj;
    }

    /*界面层需要查询的属性: 信息类型*/
    private IntoType infoTypeObj;
    public void setInfoTypeObj(IntoType infoTypeObj) {
        this.infoTypeObj = infoTypeObj;
    }
    public IntoType getInfoTypeObj() {
        return this.infoTypeObj;
    }

    /*界面层需要查询的属性: 信息标题*/
    private String infoTitle;
    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }
    public String getInfoTitle() {
        return this.infoTitle;
    }

    /*界面层需要查询的属性: 信息日期*/
    private String infoDate;
    public void setInfoDate(String infoDate) {
        this.infoDate = infoDate;
    }
    public String getInfoDate() {
        return this.infoDate;
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

    private int newsId;
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
    public int getNewsId() {
        return newsId;
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
    @Resource RoomInfoDAO roomInfoDAO;
    @Resource IntoTypeDAO intoTypeDAO;
    @Resource NewsInfoDAO newsInfoDAO;

    /*待操作的NewsInfo对象*/
    private NewsInfo newsInfo;
    public void setNewsInfo(NewsInfo newsInfo) {
        this.newsInfo = newsInfo;
    }
    public NewsInfo getNewsInfo() {
        return this.newsInfo;
    }

    /*跳转到添加NewsInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的RoomInfo信息*/
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        /*查询所有的IntoType信息*/
        List<IntoType> intoTypeList = intoTypeDAO.QueryAllIntoTypeInfo();
        ctx.put("intoTypeList", intoTypeList);
        return "add_view";
    }

    /*添加NewsInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddNewsInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomId(newsInfo.getRoomObj().getRoomId());
            newsInfo.setRoomObj(roomObj);
            IntoType infoTypeObj = intoTypeDAO.GetIntoTypeByTypeId(newsInfo.getInfoTypeObj().getTypeId());
            newsInfo.setInfoTypeObj(infoTypeObj);
            newsInfoDAO.AddNewsInfo(newsInfo);
            ctx.put("message",  java.net.URLEncoder.encode("NewsInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsInfo添加失败!"));
            return "error";
        }
    }

    /*查询NewsInfo信息*/
    public String QueryNewsInfo() {
        if(currentPage == 0) currentPage = 1;
        if(infoTitle == null) infoTitle = "";
        if(infoDate == null) infoDate = "";
        List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfoInfo(roomObj, infoTypeObj, infoTitle, infoDate, currentPage);
        /*计算总的页数和总的记录数*/
        newsInfoDAO.CalculateTotalPageAndRecordNumber(roomObj, infoTypeObj, infoTitle, infoDate);
        /*获取到总的页码数目*/
        totalPage = newsInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = newsInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsInfoList",  newsInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomObj", roomObj);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("infoTypeObj", infoTypeObj);
        List<IntoType> intoTypeList = intoTypeDAO.QueryAllIntoTypeInfo();
        ctx.put("intoTypeList", intoTypeList);
        ctx.put("infoTitle", infoTitle);
        ctx.put("infoDate", infoDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryNewsInfoOutputToExcel() { 
        if(infoTitle == null) infoTitle = "";
        if(infoDate == null) infoDate = "";
        List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfoInfo(roomObj,infoTypeObj,infoTitle,infoDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "NewsInfo信息记录"; 
        String[] headers = { "记录编号","寝室房间","信息类型","信息标题","信息日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<newsInfoList.size();i++) {
        	NewsInfo newsInfo = newsInfoList.get(i); 
        	dataset.add(new String[]{newsInfo.getNewsId() + "",newsInfo.getRoomObj().getRoomName(),
newsInfo.getInfoTypeObj().getInfoTypeName(),
newsInfo.getInfoTitle(),new SimpleDateFormat("yyyy-MM-dd").format(newsInfo.getInfoDate())});
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
			response.setHeader("Content-disposition","attachment; filename="+"NewsInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询NewsInfo信息*/
    public String FrontQueryNewsInfo() {
        if(currentPage == 0) currentPage = 1;
        if(infoTitle == null) infoTitle = "";
        if(infoDate == null) infoDate = "";
        List<NewsInfo> newsInfoList = newsInfoDAO.QueryNewsInfoInfo(roomObj, infoTypeObj, infoTitle, infoDate, currentPage);
        /*计算总的页数和总的记录数*/
        newsInfoDAO.CalculateTotalPageAndRecordNumber(roomObj, infoTypeObj, infoTitle, infoDate);
        /*获取到总的页码数目*/
        totalPage = newsInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = newsInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsInfoList",  newsInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomObj", roomObj);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("infoTypeObj", infoTypeObj);
        List<IntoType> intoTypeList = intoTypeDAO.QueryAllIntoTypeInfo();
        ctx.put("intoTypeList", intoTypeList);
        ctx.put("infoTitle", infoTitle);
        ctx.put("infoDate", infoDate);
        return "front_query_view";
    }

    /*查询要修改的NewsInfo信息*/
    public String ModifyNewsInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键newsId获取NewsInfo对象*/
        NewsInfo newsInfo = newsInfoDAO.GetNewsInfoByNewsId(newsId);

        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        List<IntoType> intoTypeList = intoTypeDAO.QueryAllIntoTypeInfo();
        ctx.put("intoTypeList", intoTypeList);
        ctx.put("newsInfo",  newsInfo);
        return "modify_view";
    }

    /*查询要修改的NewsInfo信息*/
    public String FrontShowNewsInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键newsId获取NewsInfo对象*/
        NewsInfo newsInfo = newsInfoDAO.GetNewsInfoByNewsId(newsId);

        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        List<IntoType> intoTypeList = intoTypeDAO.QueryAllIntoTypeInfo();
        ctx.put("intoTypeList", intoTypeList);
        ctx.put("newsInfo",  newsInfo);
        return "front_show_view";
    }

    /*更新修改NewsInfo信息*/
    public String ModifyNewsInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomId(newsInfo.getRoomObj().getRoomId());
            newsInfo.setRoomObj(roomObj);
            IntoType infoTypeObj = intoTypeDAO.GetIntoTypeByTypeId(newsInfo.getInfoTypeObj().getTypeId());
            newsInfo.setInfoTypeObj(infoTypeObj);
            newsInfoDAO.UpdateNewsInfo(newsInfo);
            ctx.put("message",  java.net.URLEncoder.encode("NewsInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除NewsInfo信息*/
    public String DeleteNewsInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            newsInfoDAO.DeleteNewsInfo(newsId);
            ctx.put("message",  java.net.URLEncoder.encode("NewsInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("NewsInfo删除失败!"));
            return "error";
        }
    }

}
