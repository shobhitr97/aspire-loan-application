package org.aspire.dao;

import org.aspire.dao.repository.TransactionRepository;
import org.aspire.data.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDao implements ITransactionDao {

    private final TransactionRepository repo;


    @Autowired
    public TransactionDao(TransactionRepository repo) {
        this.repo = repo;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        this.repo.insert(transaction);
    }
}
