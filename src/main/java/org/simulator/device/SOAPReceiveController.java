package org.simulator.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulator")
public class SOAPReceiveController {

	@Autowired
	private OCPP15MessageReceiveService service;

	@RequestMapping("/{protocol}")
	@ResponseBody
	public String onReceive(@PathVariable String protocol,	@RequestBody String soapXml) {
		return service.dispatch(protocol, soapXml);

	}

}
