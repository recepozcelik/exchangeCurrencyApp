package com.assignment.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }

    public  List<Account> findByAccountType(String accountType) {
        return repository.findByAccountType(accountType);
    }

    public List<Account> findByUser(String userName) {
        return repository.findByUser(userName);
    }

    @Transactional
    public Account save(Account entity) {
        return repository.save(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}