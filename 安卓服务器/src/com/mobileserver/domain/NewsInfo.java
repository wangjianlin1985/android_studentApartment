package com.mobileserver.domain;

public class NewsInfo {
    /*��¼���*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*���ҷ���*/
    private int roomObj;
    public int getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(int roomObj) {
        this.roomObj = roomObj;
    }

    /*��Ϣ����*/
    private int infoTypeObj;
    public int getInfoTypeObj() {
        return infoTypeObj;
    }
    public void setInfoTypeObj(int infoTypeObj) {
        this.infoTypeObj = infoTypeObj;
    }

    /*��Ϣ����*/
    private String infoTitle;
    public String getInfoTitle() {
        return infoTitle;
    }
    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    /*��Ϣ����*/
    private String infoContent;
    public String getInfoContent() {
        return infoContent;
    }
    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    /*��Ϣ����*/
    private java.sql.Timestamp infoDate;
    public java.sql.Timestamp getInfoDate() {
        return infoDate;
    }
    public void setInfoDate(java.sql.Timestamp infoDate) {
        this.infoDate = infoDate;
    }

}