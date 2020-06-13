package ozan.challenge.workspace.transactionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozan.challenge.workspace.transactionservice.entity.Account;
import ozan.challenge.workspace.transactionservice.entity.Rate;
import ozan.challenge.workspace.transactionservice.proxy.AccountServiceProxy;
import ozan.challenge.workspace.transactionservice.proxy.RateServiceProxy;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RefreshScope
@RestController
@RequestMapping(value="/rest")
public class FeignAccountController {

    @Autowired
    AccountServiceProxy accountServiceProxy;

    @Autowired
    RateServiceProxy rateServiceProxy;

    @RequestMapping("/account/{accountType}")
    public Optional<Account> getAccountByType(@PathVariable String accountType) {

        return accountServiceProxy.findByAccountType(accountType);

    }

    @RequestMapping("/account/{source}/{target}")
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
