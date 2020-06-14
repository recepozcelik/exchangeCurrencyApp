package com.assignment.transaction.service;

import com.assignment.transaction.model.Rate;
import com.assignment.transaction.exception.InsufficientBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.assignment.transaction.model.Transaction;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionInterface {
    private final TransactionRepository repository;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return repository.findById(id);
    }

    public List<Transaction> findByUser(String user) {
        return repository.findByUser(user);
    }

    @Transactional
    public Transaction save(Transaction entity) {
        return repository.save(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public boolean buy(Transaction transaction, List<Rate> rate) throws InsufficientBalanceException {

        BigDecimal totalAmount=transaction.getTargetAmount().divide(rate.get(1).getValue(), MathContext.DECIMAL32);

        BigDecimal availableAmount=transaction.getSourceDebit().divide(rate.get(0).getValue(),MathContext.DECIMAL32);

        if(totalAmount.compareTo(availableAmount)>0)
        {
            throw new InsufficientBalanceException("InsufficientBalance");
        }else
        {
             return true;
        }



    }
}
