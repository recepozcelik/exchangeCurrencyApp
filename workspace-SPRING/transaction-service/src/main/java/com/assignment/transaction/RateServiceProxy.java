package com.assignment.transaction;

import com.assignment.transaction.model.Rate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name="rate-service")
public interface RateServiceProxy {


    @RequestMapping("rates/{source}/{target}")
    public List<Rate> getPairRate(@PathVariable(value = "source") String source, @PathVariable(value = "target") String target);


}

