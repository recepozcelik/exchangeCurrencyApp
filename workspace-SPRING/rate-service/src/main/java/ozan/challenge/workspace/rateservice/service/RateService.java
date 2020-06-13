package ozan.challenge.workspace.rateservice.service;

import ozan.challenge.workspace.rateservice.bean.Rate;
import ozan.challenge.workspace.rateservice.bean.RateList;
import ozan.challenge.workspace.rateservice.exception.InvalidCurrencyException;

import java.util.List;


public interface RateService {

    RateList getRates(String uri);

    Rate getSpecificTypeRate(String type,String uri) throws InvalidCurrencyException;

    public List<Rate> getPairRate(String sourceType, String targetType, String uri) throws InvalidCurrencyException;
}
