package com.assignment.transaction.controller;

import com.assignment.transaction.exception.InsufficientBalanceException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.assignment.transaction.model.Account;
import com.assignment.transaction.model.Rate;
import com.assignment.transaction.model.Transaction;
import com.assignment.transaction.AccountServiceProxy;
import com.assignment.transaction.RateServiceProxy;
import com.assignment.transaction.service.TransactionServiceImp;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    TransactionServiceImp service;

    @Autowired
    AccountServiceProxy accountServiceProxy;

    @Autowired
    RateServiceProxy rateServiceProxy;

    @ApiOperation(value = "List transaction history", response = Transaction.class)
    @GetMapping
    public ResponseEntity<List<Transaction>> all() {
        return ok(service.findAll());
    }

    @ApiOperation(value = "Get specific transaction history", response = Transaction.class)
    @GetMapping("id/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(record -> ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Get transaction by user", response = Transaction.class)
    @GetMapping("/{user}")
    public List<Transaction> findByUser(@PathVariable String user) {
        return service.findByUser(user);

    }

    @ApiOperation(value = "Create transaction", response = Transaction.class)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Transaction entity) {
        return ok(service.save(entity));
    }

    @ApiOperation(value = "Update transaction", response = Transaction.class)
    @PutMapping(value="/{id}")
    public ResponseEntity<Transaction> update(@PathVariable("id") long id,
                                                                  @RequestBody Transaction obj){
        return service.findById(id)
                .map(record -> {
                    // TODO: Add update logic
                    Transaction updated = service.save(obj);
                    return ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Delete transaction", response = Transaction.class)
    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return service.findById(id)
                .map(record -> {
                    service.deleteById(id);
                    return ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Buy transaction", response = Transaction.class)
    @PostMapping("/buy")
    public Transaction buy(@Valid @RequestBody Transaction entity) throws InsufficientBalanceException {

       List<Account> sourceAccount=accountServiceProxy.findByAccountType(entity.getSourceType());
       List<Account> destinationAccount=accountServiceProxy.findByAccountType(entity.getTargetType());
       List<Rate> rates=rateServiceProxy.getPairRate(entity.getSourceType(),entity.getTargetType());
       BigDecimal ratio=rates.get(0).getValue().divide(rates.get(1).getValue(), MathContext.DECIMAL32);

        if(service.buy(entity,rates))
        {
            sourceAccount.get(0).setDebit(sourceAccount.get(0).getDebit().
                    subtract(entity.getTargetAmount().multiply(ratio)));
            accountServiceProxy.update(sourceAccount.get(0));

             if(destinationAccount.size()==0)
             {

                 Account newDestinationAccount=new Account();
                 newDestinationAccount.setDebit(entity.getTargetAmount());
                 newDestinationAccount.setAccountType(entity.getTargetType());
                 newDestinationAccount.setUserName(entity.getUserName());
                 accountServiceProxy.create(newDestinationAccount);

             }else
             {
                 destinationAccount.get(0).setDebit(destinationAccount.get(0).getDebit().add(
                         entity.getTargetAmount()));
                 accountServiceProxy.update(destinationAccount.get(0));
             }


            ok(service.save(entity));
        }
        return entity;
    }



}