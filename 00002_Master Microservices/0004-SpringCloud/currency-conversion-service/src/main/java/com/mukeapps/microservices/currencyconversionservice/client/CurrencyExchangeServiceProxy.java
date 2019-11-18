package com.mukeapps.microservices.currencyconversionservice.client;


import com.mukeapps.microservices.currencyconversionservice.mode.ExchangeValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="currency-exchange-service", url = "http://localhost:8000")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}