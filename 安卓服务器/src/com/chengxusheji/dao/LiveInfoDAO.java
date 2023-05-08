package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.RoomInfo;
import com.chengxusheji.domain.LiveInfo;

@Service @Transactional
public class LiveInfoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddLiveInfo(LiveInfo liveInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(liveInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LiveInfo> QueryLiveInfoInfo(Student studentObj,RoomInfo roomObj,String liveDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LiveInfo liveInfo where 1=1";
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and liveInfo.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	if(null != roomObj && roomObj.getRoomId()!=0) hql += " and liveInfo.roomObj.roomId=" + roomObj.getRoomId();
    	if(!liveDate.equals("")) hql = hql + " and liveInfo.liveDate like '%" + liveDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List liveInfoList = q.list();
    	return (ArrayList<LiveInfo>) liveInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LiveInfo> QueryLiveInfoInfo(Student studentObj,RoomInfo roomObj,String liveDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LiveInfo liveInfo where 1=1";
    	if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and liveInfo.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
    	if(null != roomObj && roomObj.getRoomId()!=0) hql += " and liveInfo.roomObj.roomId=" + roomObj.getRoomId();
    	if(!liveDate.equals("")) hql = hql + " and liveInfo.liveDate like '%" + liveDate + "%'";
    	Query q = s.createQuery(hql);
    	List liveInfoList = q.list();
    	return (ArrayList<LiveInfo>) liveInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LiveInfo> QueryAllLiveInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LiveInfo";
        Query q = s.createQuery(hql);
        List liveInfoList = q.list();
        return (ArrayList<LiveInfo>) liveInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Student studentObj,RoomInfo roomObj,String liveDate) {
        Session s = factory.getCurrentSession();
        String hql = "From LiveInfo liveInfo where 1=1";
        if(null != studentObj && !studentObj.getStudentNumber().equals("")) hql += " and liveInfo.studentObj.studentNumber='" + studentObj.getStudentNumber() + "'";
        if(null != roomObj && roomObj.getRoomId()!=0) hql += " and liveInfo.roomObj.roomId=" + roomObj.getRoomId();
        if(!liveDate.equals("")) hql = hql + " and liveInfo.liveDate like '%" + liveDate + "%'";
        Query q = s.createQuery(hql);
        List liveInfoList = q.list();
        recordNumber = liveInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LiveInfo GetLiveInfoByLiveInfoId(int liveInfoId) {
        Session s = factory.getCurrentSession();
        LiveInfo liveInfo = (LiveInfo)s.get(LiveInfo.class, liveInfoId);
        return liveInfo;
    }

    /*更新LiveInfo信息*/
    public void UpdateLiveInfo(LiveInfo liveInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(liveInfo);
    }

    /*删除LiveInfo信息*/
    public void DeleteLiveInfo (int liveInfoId) throws Exception {
        Session s = factory.getCurrentSession();
        Object liveInfo = s.load(LiveInfo.class, liveInfoId);
        s.delete(liveInfo);
    }

}
