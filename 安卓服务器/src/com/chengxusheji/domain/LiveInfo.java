package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*所在房间*/
    private RoomInfo roomObj;
    public RoomInfo getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }

    /*入住日期*/
    private Timestamp liveDate;
    public Timestamp getLiveDate() {
        return liveDate;
    }
    public void setLiveDate(Timestamp liveDate) {
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