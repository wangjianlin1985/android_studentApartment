package com.mobileclient.domain;

import java.io.Serializable;

public class IntoType implements Serializable {
    /*记录编号*/
    private int typeId;
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /*信息类别*/
    private String infoTypeName;
    public String getInfoTypeName() {
        return infoTypeName;
    }
    public void setInfoTypeName(String infoTypeName) {
        this.infoTypeName = infoTypeName;
    }

}