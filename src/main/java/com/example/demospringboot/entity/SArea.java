package com.example.demospringboot.entity;

import java.io.Serializable;

public class SArea implements Serializable {
    private String areaCode;

    private String areaName;

    private String areaParentCode;

    private String areaType;

    private String areaSpell;

    private String areaSts;

    private String areaShortspell;

    private String zipCode;

    private String lastChgUser;

    private String lastChgDt;

    private static final long serialVersionUID = 1L;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAreaParentCode() {
        return areaParentCode;
    }

    public void setAreaParentCode(String areaParentCode) {
        this.areaParentCode = areaParentCode == null ? null : areaParentCode.trim();
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType == null ? null : areaType.trim();
    }

    public String getAreaSpell() {
        return areaSpell;
    }

    public void setAreaSpell(String areaSpell) {
        this.areaSpell = areaSpell == null ? null : areaSpell.trim();
    }

    public String getAreaSts() {
        return areaSts;
    }

    public void setAreaSts(String areaSts) {
        this.areaSts = areaSts == null ? null : areaSts.trim();
    }

    public String getAreaShortspell() {
        return areaShortspell;
    }

    public void setAreaShortspell(String areaShortspell) {
        this.areaShortspell = areaShortspell == null ? null : areaShortspell.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
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
        sb.append(", areaCode=").append(areaCode);
        sb.append(", areaName=").append(areaName);
        sb.append(", areaParentCode=").append(areaParentCode);
        sb.append(", areaType=").append(areaType);
        sb.append(", areaSpell=").append(areaSpell);
        sb.append(", areaSts=").append(areaSts);
        sb.append(", areaShortspell=").append(areaShortspell);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", lastChgUser=").append(lastChgUser);
        sb.append(", lastChgDt=").append(lastChgDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public void clear() {
        this.areaCode = null;
        this.areaName = null;
        this.areaParentCode = null;
        this.areaType = null;
        this.areaSpell = null;
        this.areaSts = null;
        this.areaShortspell = null;
        this.zipCode = null;
        this.lastChgUser = null;
        this.lastChgDt = null;
    }
}