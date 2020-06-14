package com.assignment.account.transactionservice.service;

import com.assignment.account.transactionservice.entity.Rate;
import com.assignment.account.transactionservice.exception.InsufficientBalanceException;
import com.assignment.account.transactionservice.entity.Transaction;

import java.util.List;

public interface TransactionInterface {

    boolean buy(Transaction transaction, List<Rate> rate) throws InsufficientBalanceException;
}
