package org.simulator.chargepoint.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.mongodb.util.JSON;

public class ChargePoint {

    @Id
    String id;

    String serial;

    String centralURL;

    @SuppressWarnings("unchecked")
    List<Connector> connectors = Collections.EMPTY_LIST;

    int connectorNumber;

    String version;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    Date createDate;

    Date updateDate;

    int heartbeat;

    public String getCentralURL() {
        return centralURL;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public int getHeartbeat() {
        return heartbeat;
    }

    public String getId() {
        return id;
    }

    public String getSerial() {
        return serial;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getVersion() {
        return version;
    }

    public void setCentralURL(String centralURL) {
        this.centralURL = centralURL;
    }

    public void setConnectors(List<Connector> connectors) {
        // keep empty
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setHeartbeat(int heartbeat) {
        this.heartbeat = heartbeat;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return JSON.serialize(this);
    }

    public int getConnectorNumber() {
        return connectorNumber;
    }

    public void setConnectorNumber(int connectorNumber) {
        this.connectorNumber = connectorNumber;
        connectors = new ArrayList<Connector>(connectorNumber);
        for (int i = 0; i < connectorNumber; i++) {
            connectors.add(new Connector(i + 1, null));
        }

    }

}
