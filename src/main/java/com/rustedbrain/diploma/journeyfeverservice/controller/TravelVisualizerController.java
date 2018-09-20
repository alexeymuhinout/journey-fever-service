package com.rustedbrain.diploma.journeyfeverservice.controller;

import com.rustedbrain.diploma.journeyfeverservice.controller.service.TravelVisualizerService;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.GreetingServiceInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.dto.status.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/travel")
public class TravelVisualizerController {

    private final TravelVisualizerService controller;

    @Autowired
    public TravelVisualizerController(TravelVisualizerService controller) {
        this.controller = controller;
    }


    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public ServiceInfo protectedStatus() {
        return controller.status();
    }

    @RequestMapping(path = "/greeting", method = RequestMethod.GET)
    public GreetingServiceInfo greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return controller.greeting(name);
    }
}
