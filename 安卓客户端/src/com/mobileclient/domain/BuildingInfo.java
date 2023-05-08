package com.mobileclient.domain;

import java.io.Serializable;

public class BuildingInfo implements Serializable {
    /*记录编号*/
    private int buildingId;
    public int getBuildingId() {
        return buildingId;
    }
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /*所在校区*/
    private String areaObj;
    public String getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(String areaObj) {
        this.areaObj = areaObj;
    }

    /*宿舍名称*/
    private String buildingName;
    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /*管理员*/
    private String manageMan;
    public String getManageMan() {
        return manageMan;
    }
    public void setManageMan(String manageMan) {
        this.manageMan = manageMan;
    }

    /*门卫电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}