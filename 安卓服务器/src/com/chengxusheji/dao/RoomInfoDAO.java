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
import com.chengxusheji.domain.BuildingInfo;
import com.chengxusheji.domain.RoomInfo;

@Service @Transactional
public class RoomInfoDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddRoomInfo(RoomInfo roomInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(roomInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomInfo> QueryRoomInfoInfo(BuildingInfo buildingObj,String roomName,String roomTypeName,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RoomInfo roomInfo where 1=1";
    	if(null != buildingObj && buildingObj.getBuildingId()!=0) hql += " and roomInfo.buildingObj.buildingId=" + buildingObj.getBuildingId();
    	if(!roomName.equals("")) hql = hql + " and roomInfo.roomName like '%" + roomName + "%'";
    	if(!roomTypeName.equals("")) hql = hql + " and roomInfo.roomTypeName like '%" + roomTypeName + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List roomInfoList = q.list();
    	return (ArrayList<RoomInfo>) roomInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomInfo> QueryRoomInfoInfo(BuildingInfo buildingObj,String roomName,String roomTypeName) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RoomInfo roomInfo where 1=1";
    	if(null != buildingObj && buildingObj.getBuildingId()!=0) hql += " and roomInfo.buildingObj.buildingId=" + buildingObj.getBuildingId();
    	if(!roomName.equals("")) hql = hql + " and roomInfo.roomName like '%" + roomName + "%'";
    	if(!roomTypeName.equals("")) hql = hql + " and roomInfo.roomTypeName like '%" + roomTypeName + "%'";
    	Query q = s.createQuery(hql);
    	List roomInfoList = q.list();
    	return (ArrayList<RoomInfo>) roomInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomInfo> QueryAllRoomInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From RoomInfo";
        Query q = s.createQuery(hql);
        List roomInfoList = q.list();
        return (ArrayList<RoomInfo>) roomInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(BuildingInfo buildingObj,String roomName,String roomTypeName) {
        Session s = factory.getCurrentSession();
        String hql = "From RoomInfo roomInfo where 1=1";
        if(null != buildingObj && buildingObj.getBuildingId()!=0) hql += " and roomInfo.buildingObj.buildingId=" + buildingObj.getBuildingId();
        if(!roomName.equals("")) hql = hql + " and roomInfo.roomName like '%" + roomName + "%'";
        if(!roomTypeName.equals("")) hql = hql + " and roomInfo.roomTypeName like '%" + roomTypeName + "%'";
        Query q = s.createQuery(hql);
        List roomInfoList = q.list();
        recordNumber = roomInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public RoomInfo GetRoomInfoByRoomId(int roomId) {
        Session s = factory.getCurrentSession();
        RoomInfo roomInfo = (RoomInfo)s.get(RoomInfo.class, roomId);
        return roomInfo;
    }

    /*����RoomInfo��Ϣ*/
    public void UpdateRoomInfo(RoomInfo roomInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(roomInfo);
    }

    /*ɾ��RoomInfo��Ϣ*/
    public void DeleteRoomInfo (int roomId) throws Exception {
        Session s = factory.getCurrentSession();
        Object roomInfo = s.load(RoomInfo.class, roomId);
        s.delete(roomInfo);
    }

}
