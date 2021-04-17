package com.fred.node.chain;

import com.fred.node.monitor.NotificationService;
import com.fred.node.monitor.model.SyncStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static String STATUS_PENDING = "Pending";
    public static String STATUS_SYNCHED = "Synchronized";
    public static String STATUS_RUNNING = "Sync running";
    public static String STATUS_ERROR = "Error";

    private String status = "";
    private long lastSuccessSync = 0;
    private long lastSync = 0;
    private String errorMessage = "";
    private long consecutiveErrors = 0;
    private String syncInfo = "";

    // future versions may manage more than one node
    private String nodeID;
    private String nodeName;

    public StatusService() {
        this.status = STATUS_PENDING;
    }

    public void addNode(String id, String name) {
        this.nodeID = id;
        this.nodeName = name;
    }

    public String getStatus() {
        return status;
    }

    public long getLastSuccessSync(String nodeID) {
        return lastSuccessSync;
    }

    public long getLastSync(String nodeID) {
        return lastSync;
    }

    public void setRunning(String nodeID, String details) {
        this.status = STATUS_RUNNING;
    }

    public void setSynched(String nodeID) {
        this.errorMessage = "";
        this.lastSync = System.currentTimeMillis()/1000;
        this.lastSuccessSync = System.currentTimeMillis()/1000;
        this.status = STATUS_SYNCHED;
        this.consecutiveErrors = 0;
        this.syncInfo = "";
    }
    public void setFailed(String nodeID, String errorMessage) {
        this.errorMessage = errorMessage;
        this.lastSync = System.currentTimeMillis()/1000;
        this.status = STATUS_ERROR;
        this.consecutiveErrors++;
    }

    public SyncStatus getStatusReport(String nodeID) {
        return  new SyncStatus(nodeID, nodeName, status, lastSuccessSync, lastSync, errorMessage, consecutiveErrors, syncInfo);

    }

    public boolean isKnownNode(String nodeID) {
        if(this.nodeID != null && this.nodeID.equals(nodeID))
            return true;
        else
            return false;
    }

    public String getSyncInfo() {
        return syncInfo;
    }

    public void updateSyncInfo(String syncInfo) {
        this.syncInfo = syncInfo;
    }
}
