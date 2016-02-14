package org.simulator.audit.model;

import java.util.Calendar;
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
    
    
	@Override
	public long deleteAuditOldThan(int days) {
		Calendar c = Calendar.getInstance();
		c.roll(Calendar.DAY_OF_YEAR, -1 * days);
		// TODO Auto-generated method stub
		return repository.deleteByTimeBefore(c.getTime());
	}
}