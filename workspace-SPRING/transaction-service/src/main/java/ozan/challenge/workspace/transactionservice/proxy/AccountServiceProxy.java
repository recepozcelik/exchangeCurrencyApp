package ozan.challenge.workspace.transactionservice.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ozan.challenge.workspace.transactionservice.entity.Account;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@FeignClient(name="account-service")
public interface AccountServiceProxy {

    @RequestMapping("/accounts/{accountType}")
    Optional<Account> findByAccountType(@PathVariable String accountType);

    @RequestMapping("/accounts/{userName}")
    Optional<Account> findByUser(@PathVariable String userName);

    @PostMapping
    ResponseEntity create(@Valid @RequestBody Account entity);

    @PutMapping("accounts/")
    ResponseEntity<Account> update(@RequestBody Account obj);


}
