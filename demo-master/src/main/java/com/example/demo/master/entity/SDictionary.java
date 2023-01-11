package com.example.demo.master.entity;

import java.io.Serializable;

public class SDictionary implements Serializable {
    private String dictType;

    private String dictCode;

    private String dictTypeDesc;

    private String dictCodeDesc;

    private String dictParentType;

    private String dictParentCode;

    private Integer dictOrder;

    private String dictSts;

    private String lastChgUser;

    private String lastChgDt;

    private static final long serialVersionUID = 1L;

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }

    public String getDictTypeDesc() {
        return dictTypeDesc;
    }

    public void setDictTypeDesc(String dictTypeDesc) {
        this.dictTypeDesc = dictTypeDesc == null ? null : dictTypeDesc.trim();
    }

    public String getDictCodeDesc() {
        return dictCodeDesc;
    }

    public void setDictCodeDesc(String dictCodeDesc) {
        this.dictCodeDesc = dictCodeDesc == null ? null : dictCodeDesc.trim();
    }

    public String getDictParentType() {
        return dictParentType;
    }

    public void setDictParentType(String dictParentType) {
        this.dictParentType = dictParentType == null ? null : dictParentType.trim();
    }

    public String getDictParentCode() {
        return dictParentCode;
    }

    public void setDictParentCode(String dictParentCode) {
        this.dictParentCode = dictParentCode == null ? null : dictParentCode.trim();
    }

    public Integer getDictOrder() {
        return dictOrder;
    }

    public void setDictOrder(Integer dictOrder) {
        this.dictOrder = dictOrder;
    }

    public String getDictSts() {
        return dictSts;
    }

    public void setDictSts(String dictSts) {
        this.dictSts = dictSts == null ? null : dictSts.trim();
    }

    public String getLastChgUser() {
        return lastChgUser;
    }

    public void setLastChgUser(String lastChgUser) {
        this.lastChgUser = lastChgUser == null ? null : lastChgUser.trim();
    }

    public String getLastChgDt() {
        return lastChgDt;
    }

    public void setLastChgDt(String lastChgDt) {
        this.lastChgDt = lastChgDt == null ? null : lastChgDt.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dictType=").append(dictType);
        sb.append(", dictCode=").append(dictCode);
        sb.append(", dictTypeDesc=").append(dictTypeDesc);
        sb.append(", dictCodeDesc=").append(dictCodeDesc);
        sb.append(", dictParentType=").append(dictParentType);
        sb.append(", dictParentCode=").append(dictParentCode);
        sb.append(", dictOrder=").append(dictOrder);
        sb.append(", dictSts=").append(dictSts);
        sb.append(", lastChgUser=").append(lastChgUser);
        sb.append(", lastChgDt=").append(lastChgDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}