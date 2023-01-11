package com.example.demo.master.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "SSaler", description = "登录用户实体")
public class SSaler implements Serializable {

    @ApiModelProperty(value = "登录用户名", dataType = "String")
    private String salerId;

    @ApiModelProperty(value = "微信唯一识别码", dataType = "String")
    private String openId;

    @ApiModelProperty(value = "登录用户名", dataType = "String")
    private String salerNo;

    @ApiModelProperty(value = "用户姓名", dataType = "String")
    private String salerName;

    @ApiModelProperty(value = "登录密码", dataType = "String")
    private String password;

    @ApiModelProperty(value = "联系方式", dataType = "String")
    private String salerTel;

    @ApiModelProperty(value = "登录状态", dataType = "String")
    private String loginStatus;

    @ApiModelProperty(value = "用户状态", dataType = "String")
    private String salerStatus;

    @ApiModelProperty(value = "用户创建时间", dataType = "String")
    private String crtDt;

    @ApiModelProperty(value = "最后修改时间", dataType = "String")
    private String lastChgDt;

    @ApiModelProperty(value = "用户登录识别码", dataType = "String")
    private String sessionId;

    private static final long serialVersionUID = 1L;

    public String getSalerId() {
        return salerId;
    }

    public void setSalerId(String salerId) {
        this.salerId = salerId == null ? null : salerId.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName == null ? null : salerName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalerTel() {
        return salerTel;
    }

    public void setSalerTel(String salerTel) {
        this.salerTel = salerTel == null ? null : salerTel.trim();
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public String getSalerNo() {
        return salerNo;
    }

    public void setSalerNo(String salerNo) {
        this.salerNo = salerNo;
    }

    public String getSalerStatus() {
        return salerStatus;
    }

    public void setSalerStatus(String salerStatus) {
        this.salerStatus = salerStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", salerId=").append(salerId);
        sb.append(", openId=").append(openId);
        sb.append(", salerNo=").append(salerNo);
        sb.append(", salerName=").append(salerName);
        sb.append(", password=").append(password);
        sb.append(", salerTel=").append(salerTel);
        sb.append(", salerStatus=").append(salerStatus);
        sb.append(", loginStatus=").append(loginStatus);
        sb.append(", crtDt=").append(crtDt);
        sb.append(", lastChgDt=").append(lastChgDt);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}