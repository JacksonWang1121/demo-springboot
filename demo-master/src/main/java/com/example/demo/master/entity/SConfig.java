package com.example.demo.master.entity;

import java.io.Serializable;

public class SConfig implements Serializable {
    private Integer cfgId;

    private String cfgName;

    private String cfgValue;

    private String cfgDesc;

    private String cfgType;

    private String cfgSts;

    private String paramBlock;

    private String blockValue;

    private String crtDt;

    private String lastChgDt;

    private static final long serialVersionUID = 1L;

    public Integer getCfgId() {
        return cfgId;
    }

    public void setCfgId(Integer cfgId) {
        this.cfgId = cfgId;
    }

    public String getCfgName() {
        return cfgName;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName == null ? null : cfgName.trim();
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue == null ? null : cfgValue.trim();
    }

    public String getCfgDesc() {
        return cfgDesc;
    }

    public void setCfgDesc(String cfgDesc) {
        this.cfgDesc = cfgDesc == null ? null : cfgDesc.trim();
    }

    public String getCfgType() {
        return cfgType;
    }

    public void setCfgType(String cfgType) {
        this.cfgType = cfgType == null ? null : cfgType.trim();
    }

    public String getCfgSts() {
        return cfgSts;
    }

    public void setCfgSts(String cfgSts) {
        this.cfgSts = cfgSts == null ? null : cfgSts.trim();
    }

    public String getParamBlock() {
        return paramBlock;
    }

    public void setParamBlock(String paramBlock) {
        this.paramBlock = paramBlock == null ? null : paramBlock.trim();
    }

    public String getBlockValue() {
        return blockValue;
    }

    public void setBlockValue(String blockValue) {
        this.blockValue = blockValue == null ? null : blockValue.trim();
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt == null ? null : crtDt.trim();
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
        sb.append(", cfgId=").append(cfgId);
        sb.append(", cfgName=").append(cfgName);
        sb.append(", cfgValue=").append(cfgValue);
        sb.append(", cfgDesc=").append(cfgDesc);
        sb.append(", cfgType=").append(cfgType);
        sb.append(", cfgSts=").append(cfgSts);
        sb.append(", paramBlock=").append(paramBlock);
        sb.append(", blockValue=").append(blockValue);
        sb.append(", crtDt=").append(crtDt);
        sb.append(", lastChgDt=").append(lastChgDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}