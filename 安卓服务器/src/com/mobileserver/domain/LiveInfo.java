package com.mobileserver.domain;

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
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*���ڷ���*/
    private int roomObj;
    public int getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(int roomObj) {
        this.roomObj = roomObj;
    }

    /*��ס����*/
    private java.sql.Timestamp liveDate;
    public java.sql.Timestamp getLiveDate() {
        return liveDate;
    }
    public void setLiveDate(java.sql.Timestamp liveDate) {
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