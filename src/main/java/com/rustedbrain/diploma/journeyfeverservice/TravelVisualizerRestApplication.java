package com.rustedbrain.diploma.journeyfeverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TravelVisualizerRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelVisualizerRestApplication.class, args);
    }
}
