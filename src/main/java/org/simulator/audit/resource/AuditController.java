package org.simulator.audit.resource;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;
import java.util.List;

import org.simulator.audit.model.AuditService;
import org.simulator.audit.model.OcppAudit;
import org.simulator.audit.model.OcppAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {

	@Autowired
	private OcppAuditRepository repository;

	@Autowired
	private AuditService service;

	@RequestMapping(value = "/audit/time/{date}", method = GET)
	@ResponseBody
	public List<OcppAudit> refresh4all(@PathVariable long date) {

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "time"));

		if (date == 0L) {
			return repository.findTop50ByOrderByTimeDesc();
		} else {
			return repository.findByTimeAfter(new Date(date), sort);
		}
	}

	@RequestMapping(value = "/audit/device/{deviceSerial}/time/{date}", method = GET)
	@ResponseBody
	public List<OcppAudit> refresh(@PathVariable String deviceSerial, @PathVariable long date) {

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "time"));

		if (date == 0L) {
			return repository.findFirst50ByDeviceSerial(deviceSerial, sort);
		} else {
			return repository.findByDeviceSerialAndTimeAfter(deviceSerial, new Date(date), sort);
		}
	}

	@RequestMapping(value = "/audit/device/{deviceSerial}", method = GET)
	@ResponseBody
	public List<OcppAudit> init(@PathVariable String deviceSerial) {

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "time"));
		return repository.findFirst50ByDeviceSerial(deviceSerial, sort);

	}

	@RequestMapping(value = "/audit/device/{deviceSerial}", method = DELETE)
	@ResponseBody
	public long delete(@PathVariable String deviceSerial) {
		return repository.deleteByDeviceSerial(deviceSerial);
	}

	@RequestMapping(value = "/audit/day/{days}", method = DELETE)
	@ResponseBody
	public long deleteOldThan(@PathVariable int days) {
		return service.deleteAuditOldThan(days);
	}

}
