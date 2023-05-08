package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ClassInfo {
    /*班级编号*/
    private String classNo;
    public String getClassNo() {
        return classNo;
    }
    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    /*班级名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*班主任姓名*/
    private String banzhuren;
    public String getBanzhuren() {
        return banzhuren;
    }
    public void setBanzhuren(String banzhuren) {
        this.banzhuren = banzhuren;
    }

    /*成立日期*/
    private Timestamp beginDate;
    public Timestamp getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

}