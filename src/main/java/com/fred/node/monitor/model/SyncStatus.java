package com.fred.node.monitor.model;

public class SyncStatus {

    private String nodeID;
    private String nodeName;
    private String status;
    private long lastSuccessSync = 0;
    private long lastSync = 0;
    private String errorMessage = "";
    private long consecutiveErrors = 0;
    private String syncInfo;

    public SyncStatus(String nodeID, String nodeName, String status, long lastSuccessSync, long lastSync, String errorMessage, long consecutiveErrors, String syncInfo) {
        this.nodeID = nodeID;
        this.nodeName = nodeName;
        this.status = status;
        this.lastSuccessSync = lastSuccessSync;
        this.lastSync = lastSync;
        this.errorMessage = errorMessage;
        this.consecutiveErrors = consecutiveErrors;
        this.syncInfo = syncInfo;
    }

    public String getStatus() {
        return status;
    }

    public long getLastSuccessSync() {
        return lastSuccessSync;
    }

    public long getLastSync() {
        return lastSync;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public long getConsecutiveErrors() {
        return consecutiveErrors;
    }

    public String getNodeID() {
        return nodeID;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getSyncInfo() {
        return syncInfo;
    }

    public void setLastSuccessSync(long lastSuccessSync) {
        this.lastSuccessSync = lastSuccessSync;
    }

    public void setLastSync(long lastSync) {
        this.lastSync = lastSync;
    }

}
