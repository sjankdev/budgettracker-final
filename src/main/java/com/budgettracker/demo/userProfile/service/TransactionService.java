package com.budgettracker.demo.userProfile.service;

import com.budgettracker.demo.userProfile.models.Transaction;

public interface TransactionService {

    public void saveExpense(Transaction transaction, Long walletId, Long userId, double amount);
    public void saveIncome(Transaction transaction, Long walletId, Long userId, double amount);
}
