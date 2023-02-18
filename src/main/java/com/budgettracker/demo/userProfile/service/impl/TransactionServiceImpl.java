package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.TransactionType;
import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.TransactionRepository;
import com.budgettracker.demo.userProfile.service.TransactionService;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    public void saveExpense(Transaction transaction, Long walletId, Long userId) {
        Wallet wallet = walletService.getWalletById(walletId);

        double amount = transaction.getAmount();

        wallet.setInitialBalance(wallet.getInitialBalance() - amount);
        transaction.setTransactionType(TransactionType.EXPENSE);

        this.transactionRepository.save(transaction);
    }

    @Override
    public void saveIncome(Transaction transaction, Long walletId, Long userId) {
        Wallet wallet = walletService.getWalletById(walletId);

        double amount = transaction.getAmount();

        wallet.setInitialBalance(wallet.getInitialBalance() + amount);
        transaction.setTransactionType(TransactionType.INCOME);

        this.transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findDistinctIdByUserId(Long userId) {
        return transactionRepository.findDistinctIdByUserId(userId);
    }

    @Override
    public void deleteTransactionById(Long id) {
        this.transactionRepository.deleteById(id);
    }
}

