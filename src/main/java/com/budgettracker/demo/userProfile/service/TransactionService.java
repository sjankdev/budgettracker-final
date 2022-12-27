package com.budgettracker.demo.userProfile.service;

import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.Wallet;

public interface TransactionService {

    void saveTransaction(Transaction transaction);

}
