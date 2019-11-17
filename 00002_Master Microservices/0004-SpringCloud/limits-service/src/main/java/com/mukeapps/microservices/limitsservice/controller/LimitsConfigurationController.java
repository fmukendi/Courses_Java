package com.mukeapps.microservices.limitsservice.controller;


import com.mukeapps.microservices.limitsservice.config.AppConfiguration;
import com.mukeapps.microservices.limitsservice.model.LimitsConfiguration;
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


}
