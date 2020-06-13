package ozan.challenge.workspace.transactionservice.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozan.challenge.workspace.transactionservice.entity.Account;
import ozan.challenge.workspace.transactionservice.entity.Rate;
import ozan.challenge.workspace.transactionservice.entity.Transaction;
import ozan.challenge.workspace.transactionservice.exception.InsufficientBalanceException;
import ozan.challenge.workspace.transactionservice.proxy.AccountServiceProxy;
import ozan.challenge.workspace.transactionservice.proxy.RateServiceProxy;
import ozan.challenge.workspace.transactionservice.service.TransactionServiceImp;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("transaction")
public class TransactionController {
    private final TransactionServiceImp service;

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
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(record -> ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Get transaction by user", response = Transaction.class)
    @GetMapping("/{user}")
    public ResponseEntity<Transaction> findByUser(@PathVariable String user) {
        return service.findByUser(user)
                .map(record -> ok().body(record))
                .orElse(ResponseEntity.notFound().build());
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

       Optional<Account> sourceAccount=accountServiceProxy.findByAccountType(entity.getSourceType());
       Optional<Account> destinationAccount=accountServiceProxy.findByAccountType(entity.getTargetType());
       List<Rate> rates=rateServiceProxy.getPairRate(entity.getSourceType(),entity.getTargetType());
       BigDecimal ratio=rates.get(1).getValue().divide(rates.get(0).getValue(), RoundingMode.HALF_UP);

        if(service.buy(entity,rates))
        {
            sourceAccount.get().setDebit(sourceAccount.get().getDebit().
                    subtract(entity.getTargetAmount().multiply(ratio)));
            accountServiceProxy.update(sourceAccount.get());

            destinationAccount.get().setDebit(destinationAccount.get().getDebit().add(
                    entity.getTargetAmount()));
            accountServiceProxy.update(destinationAccount.get());
            ok(service.save(entity));
        }
        return entity;
    }



}