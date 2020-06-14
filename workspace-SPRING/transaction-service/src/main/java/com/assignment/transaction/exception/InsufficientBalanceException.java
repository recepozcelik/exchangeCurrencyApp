package com.assignment.account.transactionservice.exception;

public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message){
        super(message);
    }


}
