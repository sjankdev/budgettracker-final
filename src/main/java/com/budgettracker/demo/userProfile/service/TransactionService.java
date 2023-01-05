package com.budgettracker.demo.userProfile.service;

import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.Wallet;

public interface TransactionService {

    public void saveTransaction(Transaction transaction, Long walletId, Long userId, double amount);
}
