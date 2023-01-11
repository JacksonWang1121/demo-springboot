package com.example.demo.master.entity;

import java.io.Serializable;

public class SBaffleConfig implements Serializable {
    private String interfaceId;

    private String baffleOpen;

    private String bafflePath;

    private static final long serialVersionUID = 1L;

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId == null ? null : interfaceId.trim();
    }

    public String getBaffleOpen() {
        return baffleOpen;
    }

    public void setBaffleOpen(String baffleOpen) {
        this.baffleOpen = baffleOpen == null ? null : baffleOpen.trim();
    }

    public String getBafflePath() {
        return bafflePath;
    }

    public void setBafflePath(String bafflePath) {
        this.bafflePath = bafflePath == null ? null : bafflePath.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", interfaceId=").append(interfaceId);
        sb.append(", baffleOpen=").append(baffleOpen);
        sb.append(", bafflePath=").append(bafflePath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}