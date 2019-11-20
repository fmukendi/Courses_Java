package com.mukeapps.microservices.limitsservice.controller;


import com.mukeapps.microservices.limitsservice.config.AppConfiguration;
import com.mukeapps.microservices.limitsservice.model.LimitsConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private AppConfiguration configuration;

    @GetMapping("/limits")
    public LimitsConfiguration retrieveLimitsFromConfigurations(){

        return  new LimitsConfiguration(configuration.getMaximum(), configuration.getMinimum());
    }

    @GetMapping("/limits-static")
    public LimitsConfiguration retrieveLimitsFromConfigurationsStatic(){

        return  new LimitsConfiguration(1000, 1);
    }


    @GetMapping("/limits-with-fault-tolerance")
    @HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
    public LimitsConfiguration retrieveLimitsFromConfigurationsForSure(){

        throw  new RuntimeException("Not available");
    }


    public LimitsConfiguration fallbackRetrieveConfiguration(){

        return  new LimitsConfiguration(9999, 9);;
    }
}
