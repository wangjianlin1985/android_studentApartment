package com.mobileserver.domain;

public class BuildingInfo {
    /*��¼���*/
    private int buildingId;
    public int getBuildingId() {
        return buildingId;
    }
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /*����У��*/
    private String areaObj;
    public String getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(String areaObj) {
        this.areaObj = areaObj;
    }

    /*��������*/
    private String buildingName;
    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /*����Ա*/
    private String manageMan;
    public String getManageMan() {
        return manageMan;
    }
    public void setManageMan(String manageMan) {
        this.manageMan = manageMan;
    }

    /*�����绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}