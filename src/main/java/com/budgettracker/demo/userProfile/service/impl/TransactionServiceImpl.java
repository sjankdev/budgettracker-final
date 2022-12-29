package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.TransactionRepository;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.TransactionService;
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
    WalletRepository walletRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }

    @Override
    public double netWorthAfterOutcomeTransaction(Long userId, double amount) {
        List<Wallet> wallets = walletRepository.findDistinctIdByUserId(userId);
        double worth = 0;
        double worthAfterOutcome = 0;
        for (int i = 0; i < wallets.size(); i++)
            worth += wallets.get(i).getInitialBalance();
        worthAfterOutcome = worth - amount;
        System.out.println(worthAfterOutcome);
        return worthAfterOutcome;
    }
}
