package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private RoomInfo roomObj;
    public RoomInfo getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }

    /*��Ϣ����*/
    private IntoType infoTypeObj;
    public IntoType getInfoTypeObj() {
        return infoTypeObj;
    }
    public void setInfoTypeObj(IntoType infoTypeObj) {
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
    private Timestamp infoDate;
    public Timestamp getInfoDate() {
        return infoDate;
    }
    public void setInfoDate(Timestamp infoDate) {
        this.infoDate = infoDate;
    }

}