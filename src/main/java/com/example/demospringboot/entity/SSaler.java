package com.example.demospringboot.entity;

import java.io.Serializable;

public class SSaler implements Serializable {
    private String salerId;

    private String openId;

    private String salerName;

    private String salerTel;

    private String isLogin;

    private String crtDt;

    private String lastChgDt;

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

    public String getSalerTel() {
        return salerTel;
    }

    public void setSalerTel(String salerTel) {
        this.salerTel = salerTel == null ? null : salerTel.trim();
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin == null ? null : isLogin.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", salerId=").append(salerId);
        sb.append(", openId=").append(openId);
        sb.append(", salerName=").append(salerName);
        sb.append(", salerTel=").append(salerTel);
        sb.append(", isLogin=").append(isLogin);
        sb.append(", crtDt=").append(crtDt);
        sb.append(", lastChgDt=").append(lastChgDt);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}