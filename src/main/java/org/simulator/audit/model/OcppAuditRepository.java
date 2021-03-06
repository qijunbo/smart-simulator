package org.simulator.audit.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OcppAuditRepository extends PagingAndSortingRepository<OcppAudit, String> {
	
	public List<OcppAudit> findById(String id, Sort sort);
	
	public List<OcppAudit> findByDeviceSerial(String deviceSerial, Sort sort);
	
	public List<OcppAudit> findFirst50ByDeviceSerial(String deviceSerial, Sort sort);
	
	public List<OcppAudit> findTop50ByOrderByTimeDesc();
	
	public List<OcppAudit> findByDeviceSerialAndTimeAfter(String deviceSerial, Date date, Sort sort);
	
	public List<OcppAudit> findByTimeAfter(Date date, Sort sort);
	
	public long deleteByDeviceSerial(String deviceSerial);
	
	public long deleteByTimeBefore(Date time);
	
}
