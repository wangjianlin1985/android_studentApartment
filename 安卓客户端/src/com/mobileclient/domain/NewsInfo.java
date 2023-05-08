package com.mobileclient.domain;

import java.io.Serializable;

public class NewsInfo implements Serializable {
    /*记录编号*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*寝室房间*/
    private int roomObj;
    public int getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(int roomObj) {
        this.roomObj = roomObj;
    }

    /*信息类型*/
    private int infoTypeObj;
    public int getInfoTypeObj() {
        return infoTypeObj;
    }
    public void setInfoTypeObj(int infoTypeObj) {
        this.infoTypeObj = infoTypeObj;
    }

    /*信息标题*/
    private String infoTitle;
    public String getInfoTitle() {
        return infoTitle;
    }
    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    /*信息内容*/
    private String infoContent;
    public String getInfoContent() {
        return infoContent;
    }
    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    /*信息日期*/
    private java.sql.Timestamp infoDate;
    public java.sql.Timestamp getInfoDate() {
        return infoDate;
    }
    public void setInfoDate(java.sql.Timestamp infoDate) {
        this.infoDate = infoDate;
    }

}