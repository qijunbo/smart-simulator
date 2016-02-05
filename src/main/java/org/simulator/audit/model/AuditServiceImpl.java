package org.simulator.audit.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditServiceImpl implements AuditService {

    @Autowired
    private OcppAuditRepository repository;

    @Override
    public OcppAudit auditRequest(String deviceSerial, String message) {

        return repository.save(new OcppAudit(deviceSerial, OcppAudit.MessageType.request, message, new Date()));
    }

    @Override
    public OcppAudit auditResponse(String deviceSerial, String message) {
        return repository.save(new OcppAudit(deviceSerial, OcppAudit.MessageType.response, message, new Date()));
    }
}