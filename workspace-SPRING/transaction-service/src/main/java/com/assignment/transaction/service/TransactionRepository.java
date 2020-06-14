package com.assignment.transaction.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.assignment.transaction.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select u from Transaction u where u.userName = ?1")
    List<Transaction> findByUser(String userName);
}