package com.assignment.transaction.controller;

import com.assignment.transaction.AccountServiceProxy;
import com.assignment.transaction.RateServiceProxy;
import com.assignment.transaction.model.Account;
import com.assignment.transaction.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping(value="/rest")
public class FeignAccountController {

    @Autowired
    AccountServiceProxy accountServiceProxy;

    @Autowired
    RateServiceProxy rateServiceProxy;

    @RequestMapping("/accounts/{accountType}")
    public  List<Account> getAccountByType(@PathVariable String accountType) {

        return accountServiceProxy.findByAccountType(accountType);

    }

    @RequestMapping("/rates/{source}/{target}")
    public List<Rate> getPairRate(@PathVariable(value = "source") String source, @PathVariable(value = "target") String target)
    {
        return rateServiceProxy.getPairRate(source,target);
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Account entity){

       return accountServiceProxy.create(entity);

    }


    @PutMapping("/")
    public ResponseEntity<Account> update(@RequestBody Account obj){

        return accountServiceProxy.update(obj);

    }



}
