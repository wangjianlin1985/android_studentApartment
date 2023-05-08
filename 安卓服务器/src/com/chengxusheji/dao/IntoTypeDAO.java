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
import com.chengxusheji.domain.IntoType;

@Service @Transactional
public class IntoTypeDAO {

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
    public void AddIntoType(IntoType intoType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(intoType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<IntoType> QueryIntoTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From IntoType intoType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List intoTypeList = q.list();
    	return (ArrayList<IntoType>) intoTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<IntoType> QueryIntoTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From IntoType intoType where 1=1";
    	Query q = s.createQuery(hql);
    	List intoTypeList = q.list();
    	return (ArrayList<IntoType>) intoTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<IntoType> QueryAllIntoTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From IntoType";
        Query q = s.createQuery(hql);
        List intoTypeList = q.list();
        return (ArrayList<IntoType>) intoTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From IntoType intoType where 1=1";
        Query q = s.createQuery(hql);
        List intoTypeList = q.list();
        recordNumber = intoTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public IntoType GetIntoTypeByTypeId(int typeId) {
        Session s = factory.getCurrentSession();
        IntoType intoType = (IntoType)s.get(IntoType.class, typeId);
        return intoType;
    }

    /*更新IntoType信息*/
    public void UpdateIntoType(IntoType intoType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(intoType);
    }

    /*删除IntoType信息*/
    public void DeleteIntoType (int typeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object intoType = s.load(IntoType.class, typeId);
        s.delete(intoType);
    }

}
