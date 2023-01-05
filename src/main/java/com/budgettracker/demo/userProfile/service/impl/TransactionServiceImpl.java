package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.TransactionRepository;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.TransactionService;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Component
@EnableAutoConfiguration
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletService walletService;

    @Override
    public void saveTransaction(Transaction transaction, Long walletId, Long userId, double amount) {
        Wallet wallet = walletService.getWalletById(walletId);

        wallet.setInitialBalance(wallet.getInitialBalance() - amount);

        System.out.println(wallet);
        this.transactionRepository.save(transaction);
    }

}

