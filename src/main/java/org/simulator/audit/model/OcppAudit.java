package org.simulator.audit.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class OcppAudit {

    public static enum MessageType {
        request, response
    }

    private static final String template = "[%s]: ID: [%s], DeviceSerial:[%s] \n[%s]: [%s] ";

    @Id
    String id;

    String deviceSerial;

    String transactionId;

    MessageType type;

    String message;

    Date time;

    public OcppAudit() {
    }

    public OcppAudit(String deviceSerial, MessageType type, String message, Date time) {
        super();

        this.deviceSerial = deviceSerial;
        this.type = type;
        this.message = message;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {

        return String.format(template, this.getClass().getName(), this.getId(), getDeviceSerial(), getType().name(),
                getMessage());
    }
}
