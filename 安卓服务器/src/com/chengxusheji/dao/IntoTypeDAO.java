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
    public void AddIntoType(IntoType intoType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(intoType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<IntoType> QueryIntoTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From IntoType intoType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
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

    /*�����ܵ�ҳ���ͼ�¼��*/
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

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public IntoType GetIntoTypeByTypeId(int typeId) {
        Session s = factory.getCurrentSession();
        IntoType intoType = (IntoType)s.get(IntoType.class, typeId);
        return intoType;
    }

    /*����IntoType��Ϣ*/
    public void UpdateIntoType(IntoType intoType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(intoType);
    }

    /*ɾ��IntoType��Ϣ*/
    public void DeleteIntoType (int typeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object intoType = s.load(IntoType.class, typeId);
        s.delete(intoType);
    }

}
