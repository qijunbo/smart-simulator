package org.simulator.audit.resource;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;
import java.util.List;

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

	@RequestMapping(value = "/audit/device/{deviceSerial}/time/{date}", method = GET)
	@ResponseBody
	public List<OcppAudit> refresh(@PathVariable String deviceSerial, @PathVariable long date) {

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "time"));
		return repository.findByDeviceSerialAndTimeAfter(deviceSerial, new Date(date), sort);

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

}
