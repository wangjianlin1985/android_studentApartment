package com.mobileclient.domain;

import java.io.Serializable;

public class RoomInfo implements Serializable {
    /*记录编号*/
    private int roomId;
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*所在宿舍*/
    private int buildingObj;
    public int getBuildingObj() {
        return buildingObj;
    }
    public void setBuildingObj(int buildingObj) {
        this.buildingObj = buildingObj;
    }

    /*房间名称*/
    private String roomName;
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*房间类型*/
    private String roomTypeName;
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    /*房间价格(元/月)*/
    private float roomPrice;
    public float getRoomPrice() {
        return roomPrice;
    }
    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }

    /*总床位*/
    private int totalBedNumber;
    public int getTotalBedNumber() {
        return totalBedNumber;
    }
    public void setTotalBedNumber(int totalBedNumber) {
        this.totalBedNumber = totalBedNumber;
    }

    /*剩余床位*/
    private int leftBedNum;
    public int getLeftBedNum() {
        return leftBedNum;
    }
    public void setLeftBedNum(int leftBedNum) {
        this.leftBedNum = leftBedNum;
    }

    /*寝室电话*/
    private String roomTelephone;
    public String getRoomTelephone() {
        return roomTelephone;
    }
    public void setRoomTelephone(String roomTelephone) {
        this.roomTelephone = roomTelephone;
    }

    /*附加信息*/
    private String roomMemo;
    public String getRoomMemo() {
        return roomMemo;
    }
    public void setRoomMemo(String roomMemo) {
        this.roomMemo = roomMemo;
    }

}