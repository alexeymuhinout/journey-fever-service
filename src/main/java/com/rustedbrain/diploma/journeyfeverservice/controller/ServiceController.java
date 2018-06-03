package com.rustedbrain.diploma.journeyfeverservice.controller;

import com.rustedbrain.diploma.journeyfeverservice.model.GreetingServiceStatusInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.ServiceStatusInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.Status;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RequestMapping("/rest")
@RestController
public class ServiceController {

    private static final String GREETING_TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public ServiceStatusInfo status() {
        return new ServiceStatusInfo(counter.incrementAndGet(), Status.OK, System.currentTimeMillis());
    }

    @RequestMapping(path = "/status/greeting", method = RequestMethod.GET)
    public GreetingServiceStatusInfo greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new GreetingServiceStatusInfo(counter.incrementAndGet(), Status.OK, System.currentTimeMillis(), String.format(GREETING_TEMPLATE, name));
    }


}
