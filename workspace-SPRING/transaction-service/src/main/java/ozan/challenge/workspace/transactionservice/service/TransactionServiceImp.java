package ozan.challenge.workspace.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ozan.challenge.workspace.transactionservice.entity.Rate;
import ozan.challenge.workspace.transactionservice.entity.Transaction;
import ozan.challenge.workspace.transactionservice.exception.InsufficientBalanceException;

import java.math.BigDecimal;
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

    public Optional<Transaction> findByUser(String user) {
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

        BigDecimal totalAmount=rate.get(1).getValue().multiply(transaction.getTargetAmount());

        BigDecimal availableAmount=rate.get(0).getValue().multiply(transaction.getSourceAmount());

        if(totalAmount.compareTo(availableAmount)>0)
        {
            throw new InsufficientBalanceException("InsufficientBalance");
        }else
        {
             return true;
        }



    }
}
