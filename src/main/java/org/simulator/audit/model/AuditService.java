package org.simulator.audit.model;

public interface AuditService {

    public abstract OcppAudit auditRequest(String deviceSerial,  String message);

    public abstract OcppAudit auditResponse(String deviceSerial, String message);

}