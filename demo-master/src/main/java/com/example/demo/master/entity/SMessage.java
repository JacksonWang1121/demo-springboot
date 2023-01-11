package com.example.demo.master.entity;

import java.io.Serializable;

public class SMessage implements Serializable {
    private String msgCode;

    private String msgDesc;

    private String msgLevel;

    private String msgType;

    private String msgSts;

    private String lastChgUser;

    private String lastChgDt;

    private static final long serialVersionUID = 1L;

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode == null ? null : msgCode.trim();
    }

    public String getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(String msgDesc) {
        this.msgDesc = msgDesc == null ? null : msgDesc.trim();
    }

    public String getMsgLevel() {
        return msgLevel;
    }

    public void setMsgLevel(String msgLevel) {
        this.msgLevel = msgLevel == null ? null : msgLevel.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getMsgSts() {
        return msgSts;
    }

    public void setMsgSts(String msgSts) {
        this.msgSts = msgSts == null ? null : msgSts.trim();
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
        sb.append(", msgCode=").append(msgCode);
        sb.append(", msgDesc=").append(msgDesc);
        sb.append(", msgLevel=").append(msgLevel);
        sb.append(", msgType=").append(msgType);
        sb.append(", msgSts=").append(msgSts);
        sb.append(", lastChgUser=").append(lastChgUser);
        sb.append(", lastChgDt=").append(lastChgDt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}