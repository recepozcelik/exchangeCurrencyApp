package com.assignment.account;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@Api(value="account service", description="Operations pertaining to account")
public class AccountController {
    private final AccountService service;

    @ApiOperation(value = "View a list of available accounts", response = Account.class)
    @GetMapping
    public ResponseEntity<List<Account>> all() {
        return ok(service.findAll());
    }

    @ApiOperation(value = "Get specific account type", response = Account.class)
    @GetMapping("user/{accountType}")
    public List<Account> findByAccountType(@PathVariable String accountType) {
        return service.findByAccountType(accountType);

    }

    @ApiOperation(value = "Get user accounts", response = Account.class)
    @GetMapping("/current")
    public List<Account> getCurrentAccount(Principal principal) {
        return service.findByUser(principal.getName());
    }


    @ApiOperation(value = "Get user accounts", response = Account.class)
    @GetMapping("/{userName}")
    public List<Account> findByUser(@PathVariable String userName) {
        return service.findByUser(userName);
    }

    @ApiOperation(value = "Create user accounts", response = Account.class)
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Account entity) {
        return ok(service.save(entity));
    }

    @ApiOperation(value = "Update user accounts", response = Account.class)
    @PutMapping("/")
    public ResponseEntity<Account> update(@RequestBody Account obj){
        return service.findById(obj.getId())
                .map(record -> {
                    // TODO: Add update logic
                    record=obj;
                    Account updated = service.save(record);
                    return ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Delete user account", response = Account.class)
    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return service.findById(id)
                .map(record -> {
                    service.deleteById(id);
                    return ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}