package com.mobileserver.domain;

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
    private java.sql.Timestamp beginDate;
    public java.sql.Timestamp getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(java.sql.Timestamp beginDate) {
        this.beginDate = beginDate;
    }

}