package com.mobileserver.domain;

public class LiveInfo {
    /*记录编号*/
    private int liveInfoId;
    public int getLiveInfoId() {
        return liveInfoId;
    }
    public void setLiveInfoId(int liveInfoId) {
        this.liveInfoId = liveInfoId;
    }

    /*学生*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*所在房间*/
    private int roomObj;
    public int getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(int roomObj) {
        this.roomObj = roomObj;
    }

    /*入住日期*/
    private java.sql.Timestamp liveDate;
    public java.sql.Timestamp getLiveDate() {
        return liveDate;
    }
    public void setLiveDate(java.sql.Timestamp liveDate) {
        this.liveDate = liveDate;
    }

    /*附加信息*/
    private String liveMemo;
    public String getLiveMemo() {
        return liveMemo;
    }
    public void setLiveMemo(String liveMemo) {
        this.liveMemo = liveMemo;
    }

}