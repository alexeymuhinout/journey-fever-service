package com.rustedbrain.diploma.journeyfeverservice.controller.service;

import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;

public interface TravelVisualizerService {

    ServiceInfo status();

    GreetingServiceInfo greeting(String name);

    Boolean login(String username, String password);
}
