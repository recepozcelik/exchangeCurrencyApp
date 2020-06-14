package com.assignment.transaction.service;

import com.assignment.transaction.model.Rate;
import com.assignment.transaction.exception.InsufficientBalanceException;
import com.assignment.transaction.model.Transaction;

import java.util.List;

public interface TransactionInterface {

    boolean buy(Transaction transaction, List<Rate> rate) throws InsufficientBalanceException;
}
