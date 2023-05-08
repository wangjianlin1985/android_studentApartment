package com.chengxusheji.domain;

import java.sql.Timestamp;
public class RoomInfo {
    /*��¼���*/
    private int roomId;
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /*��������*/
    private BuildingInfo buildingObj;
    public BuildingInfo getBuildingObj() {
        return buildingObj;
    }
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }

    /*��������*/
    private String roomName;
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /*��������*/
    private String roomTypeName;
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    /*����۸�(Ԫ/��)*/
    private float roomPrice;
    public float getRoomPrice() {
        return roomPrice;
    }
    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }

    /*�ܴ�λ*/
    private int totalBedNumber;
    public int getTotalBedNumber() {
        return totalBedNumber;
    }
    public void setTotalBedNumber(int totalBedNumber) {
        this.totalBedNumber = totalBedNumber;
    }

    /*ʣ�ലλ*/
    private int leftBedNum;
    public int getLeftBedNum() {
        return leftBedNum;
    }
    public void setLeftBedNum(int leftBedNum) {
        this.leftBedNum = leftBedNum;
    }

    /*���ҵ绰*/
    private String roomTelephone;
    public String getRoomTelephone() {
        return roomTelephone;
    }
    public void setRoomTelephone(String roomTelephone) {
        this.roomTelephone = roomTelephone;
    }

    /*������Ϣ*/
    private String roomMemo;
    public String getRoomMemo() {
        return roomMemo;
    }
    public void setRoomMemo(String roomMemo) {
        this.roomMemo = roomMemo;
    }

}