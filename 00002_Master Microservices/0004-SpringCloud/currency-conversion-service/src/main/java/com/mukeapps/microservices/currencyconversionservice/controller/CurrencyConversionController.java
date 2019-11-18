package com.mukeapps.microservices.currencyconversionservice.controller;


import com.mukeapps.microservices.currencyconversionservice.client.CurrencyExchangeServiceProxy;
import com.mukeapps.microservices.currencyconversionservice.mode.CurrencyConversionBean;
import com.mukeapps.microservices.currencyconversionservice.mode.ExchangeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        ExchangeValue response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
        return new CurrencyConversionBean(response.getId(), from, to, response.getconversionMultiple(),
                quantity, quantity.multiply(response.getconversionMultiple()), response.getPort());
    }

    @GetMapping("/currency-converter-old/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyOld(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/USD/to/INR",
                CurrencyConversionBean.class,
                uriVariables
        );

        CurrencyConversionBean response = responseEntity.getBody();

//        return new CurrencyConversionBean(1L, from, to, BigDecimal.ONE, quantity, quantity, 8100);
        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(),
                quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
    }
}
