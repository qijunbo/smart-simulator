package org.simulator.time;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class TimeController {

	@RequestMapping(value = "/number/{time}", method = GET)
	@ResponseBody
	public String fromLong(@PathVariable long time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
		return dateFormat.format(new Date(time));
	}

	@RequestMapping(value = "/string/{time}", method = GET)
	@ResponseBody
	public long fromString(@PathVariable @DateTimeFormat( pattern="yyyy-MM-dd'T'HH:mm:ss:SSSZ") Date time) {
		return time.getTime();
	}
}
