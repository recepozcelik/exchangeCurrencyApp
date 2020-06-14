package com.assignment.account.transactionservice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.assignment.account.transactionservice.entity.Transaction;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select u from Transaction u where u.userName = ?1")
    Optional<Transaction> findByUser(String userName);
}