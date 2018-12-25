package com.in28minutes.microservices.currencyconversionservice.controller;

import com.in28minutes.microservices.currencyconversionservice.entity.CurrencyConversionBean;
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

    @GetMapping(value = "/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("to", to);
        uriVariables.put("from", from);
        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                uriVariables);
        CurrencyConversionBean response = responseEntity.getBody();

        CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(response.getId(), from, to,
                response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()),
                8000);
        return currencyConversionBean;
    }
}
