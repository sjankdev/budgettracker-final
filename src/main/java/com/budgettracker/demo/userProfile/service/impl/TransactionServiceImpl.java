package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.repository.TransactionRepository;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@EnableAutoConfiguration
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }
}
