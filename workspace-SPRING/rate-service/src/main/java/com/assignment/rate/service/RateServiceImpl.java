package com.assignment.rate.service;


import com.assignment.rate.exception.InvalidCurrencyException;
import com.assignment.rate.model.Rate;
import com.assignment.rate.model.RateList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class RateServiceImpl implements RateService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public RateList getRates(String uri)  {

        RateList rates = fetchInformation(uri).getBody();
        return rates;
    }

    @Override
    public Rate getSpecificTypeRate(String type, String uri) throws InvalidCurrencyException {

        RateList rates = fetchInformation(uri).getBody();
        Rate rate=new Rate();

        if(rates.getRates().containsKey(type))
        {
            rate.setName(type);
            rate.setValue(rates.getRates().get(type));
        }else
        {
            throw new InvalidCurrencyException("Invalid Currency");
        }
        return rate;
    }

    @Override
    public List<Rate> getPairRate(String sourceType,String targetType,String uri) throws InvalidCurrencyException {

        RateList rates = fetchInformation(uri).getBody();
        List<Rate> rate=new ArrayList<>();
        Rate source=new Rate();
        Rate target=new Rate();

        if(rates.getRates().containsKey(sourceType))
        {
            source.setName(sourceType);
            source.setValue(rates.getRates().get(sourceType));
            rate.add(source);
        }else
        {
            throw new InvalidCurrencyException("Invalid Currency");
        }
        if(rates.getRates().containsKey(targetType))
        {
            target.setName(targetType);
            target.setValue(rates.getRates().get(targetType));
            rate.add(target);
        }else
        {
            throw new InvalidCurrencyException("Invalid Currency");
        }

        return rate;
    }

    private ResponseEntity<RateList> fetchInformation(String uri)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RateList> rateResponse = restTemplate.exchange(uri,
                HttpMethod.GET, entity, new ParameterizedTypeReference<RateList>() {
                });

        return rateResponse;
    }

}
