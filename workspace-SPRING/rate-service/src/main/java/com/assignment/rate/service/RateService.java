package com.assignment.rate.service;

import com.assignment.rate.exception.InvalidCurrencyException;
import com.assignment.rate.model.Rate;
import com.assignment.rate.model.RateList;

import java.util.List;


public interface RateService {

    RateList getRates(String uri);

    Rate getSpecificTypeRate(String type, String uri) throws InvalidCurrencyException;

    public List<Rate> getPairRate(String sourceType, String targetType, String uri) throws InvalidCurrencyException;
}
