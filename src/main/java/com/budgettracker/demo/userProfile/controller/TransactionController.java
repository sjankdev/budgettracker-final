package com.budgettracker.demo.userProfile.controller;

import com.budgettracker.demo.security.token.services.UserDetailsImpl;
import com.budgettracker.demo.userProfile.models.*;
import com.budgettracker.demo.userProfile.repository.TransactionRepository;
import com.budgettracker.demo.userProfile.service.TransactionService;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/incomeTransaction/{walletId}")
    public String incomeTransaction(@PathVariable(value = "walletId") long walletId, Transaction transaction, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);


        model.addAttribute("wallet", walletService.getWalletById(walletId));
        model.addAttribute("transaction", transaction);
        model.addAttribute("incomeCategories", IncomeCategories.values());

        return "income_transaction";

    }

    @GetMapping("/expenseTransaction/{walletId}")
    public String expenseTransaction(@PathVariable(value = "walletId") long walletId, Transaction transaction, Wallet wallet, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);

        model.addAttribute("wallet", walletService.getWalletById(walletId));
        model.addAttribute("transaction", transaction);
        model.addAttribute("expenseCategories", ExpenseCategories.values());

        return "expense_transaction";

    }

    @PostMapping("/saveExpense/{walletId}")
    public String saveExpense(@PathVariable(value = "walletId") long walletId, @Valid Transaction transaction, BindingResult result, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        Wallet wallet = walletService.getWalletById(walletId);

        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            model.addAttribute("expenseCategories", ExpenseCategories.values());
            return "expense_transaction";
        }

        transaction.setWallet(wallet);
        transaction.setUserId(userId);
        transactionService.saveExpense(transaction, walletId, userId);
        return "redirect:/api/wallet/userWallet/balance/" + userId;

    }

    @PostMapping("/saveIncome/{walletId}")
    public String saveIncome(@PathVariable(value = "walletId") long walletId, @Valid Transaction transaction, BindingResult result, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        Wallet wallet = walletService.getWalletById(walletId);

        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            model.addAttribute("incomeCategories", IncomeCategories.values());
            return "income_transaction";
        }

        transaction.setWallet(wallet);
        transaction.setUserId(userId);
        transaction.setWalletName(wallet.getWalletName());
        transactionService.saveIncome(transaction, walletId, userId);
        return "redirect:/api/wallet/userWallet/balance/" + userId;
    }

    @GetMapping("/userTransactions/{user_id}")
    public String getUserTransactions(@PathVariable("user_id") long user_id, TransactionGroup transactionGroup, Model model) {
        List<Transaction> transactions = transactionRepository.getTransactionsByUserId(user_id);
        List<TransactionGroup> transactionByDate = new ArrayList<>();
        List<Transaction> transOnSingleDate = new ArrayList<>();
        LocalDate currDate = transactions.get(0).getDate();

        TransactionGroup transGroup = new TransactionGroup();

        for (Transaction t : transactions) {
            if (!currDate.isEqual(t.getDate())) {
                transGroup.setDate(currDate);
                transGroup.setTransactions(transOnSingleDate);
                transactionByDate.add(transGroup);
                transGroup = new TransactionGroup();
                transOnSingleDate = new ArrayList<>();
            }

            transOnSingleDate.add(t);
            currDate = t.getDate();
        }
        transGroup.setDate(currDate);
        transGroup.setTransactions(transOnSingleDate);

        transactionByDate.add(transGroup);

        model.addAttribute("transactionGroup", transactionByDate);
        return "transactions";
    }
}


