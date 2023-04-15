package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.*;
import com.budgettracker.demo.userProfile.repository.TransactionRepository;
import com.budgettracker.demo.userProfile.service.TransactionService;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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
        amount = (wallet.getWalletBalance() == null ? wallet.getLastUpdatedInitialBalance() : wallet.getWalletBalance()) - amount;
        wallet.setWalletBalance(amount);

        System.out.println("Wallet balance after expense " + wallet.getWalletBalance());

        transaction.setTransactionType(TransactionType.EXPENSE);

        this.transactionRepository.save(transaction);
    }

    @Override
    public void saveIncome(Transaction transaction, Long walletId, Long userId) {
        Wallet wallet = walletService.getWalletById(walletId);

        double amount = transaction.getAmount();
        amount += wallet.getWalletBalance() == null ? wallet.getLastUpdatedInitialBalance() : wallet.getWalletBalance();
        wallet.setWalletBalance(amount);

        System.out.println("Wallet balance after income " + wallet.getWalletBalance());

        transaction.setTransactionType(TransactionType.INCOME);

        this.transactionRepository.save(transaction);
    }

    @Override
    public void updateIncome(Transaction transaction, Long transactionId) {
        Transaction existingTransactionInDb = getTransactionById(transactionId);
        double amount = transaction.getAmount();
        String note = transaction.getNote();
        LocalDate date = transaction.getDate();
        String income = String.valueOf(transaction.getIncomeCategories());
        System.out.println(transaction.getAmount());


        if (amount < existingTransactionInDb.getAmount()) {
            existingTransactionInDb.getWallet().setWalletBalance
                    (existingTransactionInDb.getWallet().getWalletBalance() + (amount - existingTransactionInDb.getAmount()));
        } else {
            existingTransactionInDb.getWallet().setWalletBalance
                    (existingTransactionInDb.getWallet().getWalletBalance() + (amount - existingTransactionInDb.getAmount()));
        }
        existingTransactionInDb.setNote(note);
        existingTransactionInDb.setAmount(amount);
        existingTransactionInDb.setDate(date);
        existingTransactionInDb.setIncomeCategories(IncomeCategories.valueOf(income));
        this.transactionRepository.save(existingTransactionInDb);
    }

    @Override
    public void updateExpense(Transaction transaction, Long transactionId) {
        Transaction existingTransactionInDb = getTransactionById(transactionId);
        double amount = transaction.getAmount();
        String note = transaction.getNote();
        LocalDate date = transaction.getDate();
        String expense = String.valueOf(transaction.getExpenseCategories());
        System.out.println(transaction.getAmount());

        existingTransactionInDb.getWallet().setWalletBalance
                (existingTransactionInDb.getWallet().getWalletBalance() - (amount - existingTransactionInDb.getAmount()));
        existingTransactionInDb.setNote(note);
        existingTransactionInDb.setAmount(amount);
        existingTransactionInDb.setDate(date);
        existingTransactionInDb.setExpenseCategories(ExpenseCategories.valueOf(expense));
        this.transactionRepository.save(existingTransactionInDb);
    }

    @Override
    public List<Transaction> findDistinctIdByUserId(Long userId) {
        return transactionRepository.findDistinctIdByUserId(userId);
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        Transaction existingTransactionInDb = getTransactionById(transactionId);
        double amount = existingTransactionInDb.getAmount();


        if (existingTransactionInDb.getTransactionType().getDisplayName().equalsIgnoreCase("income")) {
            existingTransactionInDb.getWallet().setInitialBalance
                    (existingTransactionInDb.getWallet()
                            .getInitialBalance() - amount);
        }
        if(existingTransactionInDb.getTransactionType().getDisplayName().equalsIgnoreCase("expense")){
            existingTransactionInDb.getWallet().setInitialBalance
                    (existingTransactionInDb.getWallet()
                            .getInitialBalance() + amount);
        }

        this.transactionRepository.deleteById(transactionId);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        Optional<Transaction> optional = transactionRepository.findById(id);
        Transaction transaction = null;
        if (optional.isPresent()) {
            transaction = optional.get();
        } else {
            throw new RuntimeException(" Transaction not found for id :: " + id);
        }
        return transaction;
    }
}


