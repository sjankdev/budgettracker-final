package com.budgettracker.demo.userProfile.service;

import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.Wallet;

import java.util.List;

public interface TransactionService {

    List<Transaction> findDistinctIdByUserId(Long userId);

    public void saveExpense(Transaction transaction, Long walletId, Long userId);

    public void saveIncome(Transaction transaction, Long walletId, Long userId);

    public void updateIncome(Transaction transaction, Long transactionId);

    void deleteTransactionById(Long id);

    Transaction getTransactionById(Long id);
}
