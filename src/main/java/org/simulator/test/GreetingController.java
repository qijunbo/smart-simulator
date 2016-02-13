package org.simulator.test;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Testing device on [%s] protocal, device id: %s!";

	@RequestMapping("/test/{protocol}/{deviceid}")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name,
			@PathVariable String protocol, @PathVariable String deviceid) {
		return String.format(template, protocol, deviceid);
	}
}
