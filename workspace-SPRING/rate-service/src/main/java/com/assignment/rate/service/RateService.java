package com.assignment.account.service;

import com.assignment.account.exception.InvalidCurrencyException;
import com.assignment.account.model.Rate;
import com.assignment.account.model.RateList;

import java.util.List;


public interface RateService {

    RateList getRates(String uri);

    Rate getSpecificTypeRate(String type, String uri) throws InvalidCurrencyException;

    public List<Rate> getPairRate(String sourceType, String targetType, String uri) throws InvalidCurrencyException;
}
