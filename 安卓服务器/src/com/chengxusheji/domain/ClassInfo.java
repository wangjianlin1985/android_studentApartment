package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ClassInfo {
    /*�༶���*/
    private String classNo;
    public String getClassNo() {
        return classNo;
    }
    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    /*�༶����*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*����������*/
    private String banzhuren;
    public String getBanzhuren() {
        return banzhuren;
    }
    public void setBanzhuren(String banzhuren) {
        this.banzhuren = banzhuren;
    }

    /*��������*/
    private Timestamp beginDate;
    public Timestamp getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

}