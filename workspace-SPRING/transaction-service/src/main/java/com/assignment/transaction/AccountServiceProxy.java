package com.assignment.transaction.proxy;


import com.assignment.transaction.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@FeignClient(name="account-service")
public interface AccountServiceProxy {

    @RequestMapping("/accounts/{accountType}")
    List<Account> findByAccountType(@PathVariable String accountType);

    @RequestMapping("/accounts/{userName}")
    Optional<Account> findByUser(@PathVariable String userName);

    @PostMapping("/accounts")
    ResponseEntity create(@Valid @RequestBody Account entity);

    @PutMapping("accounts/")
    ResponseEntity<Account> update(@RequestBody Account obj);


}
