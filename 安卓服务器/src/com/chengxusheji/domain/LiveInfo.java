package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LiveInfo {
    /*��¼���*/
    private int liveInfoId;
    public int getLiveInfoId() {
        return liveInfoId;
    }
    public void setLiveInfoId(int liveInfoId) {
        this.liveInfoId = liveInfoId;
    }

    /*ѧ��*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*���ڷ���*/
    private RoomInfo roomObj;
    public RoomInfo getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }

    /*��ס����*/
    private Timestamp liveDate;
    public Timestamp getLiveDate() {
        return liveDate;
    }
    public void setLiveDate(Timestamp liveDate) {
        this.liveDate = liveDate;
    }

    /*������Ϣ*/
    private String liveMemo;
    public String getLiveMemo() {
        return liveMemo;
    }
    public void setLiveMemo(String liveMemo) {
        this.liveMemo = liveMemo;
    }

}