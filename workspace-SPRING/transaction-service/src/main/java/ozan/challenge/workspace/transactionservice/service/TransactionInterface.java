package ozan.challenge.workspace.transactionservice.service;

import ozan.challenge.workspace.transactionservice.entity.Rate;
import ozan.challenge.workspace.transactionservice.entity.Transaction;
import ozan.challenge.workspace.transactionservice.exception.InsufficientBalanceException;

import java.util.List;

public interface TransactionInterface {

    boolean buy(Transaction transaction, List<Rate> rate) throws InsufficientBalanceException;
}
