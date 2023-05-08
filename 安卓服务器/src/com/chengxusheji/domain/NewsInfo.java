package com.chengxusheji.domain;

import java.sql.Timestamp;
public class NewsInfo {
    /*记录编号*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*寝室房间*/
    private RoomInfo roomObj;
    public RoomInfo getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }

    /*信息类型*/
    private IntoType infoTypeObj;
    public IntoType getInfoTypeObj() {
        return infoTypeObj;
    }
    public void setInfoTypeObj(IntoType infoTypeObj) {
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
    private Timestamp infoDate;
    public Timestamp getInfoDate() {
        return infoDate;
    }
    public void setInfoDate(Timestamp infoDate) {
        this.infoDate = infoDate;
    }

}