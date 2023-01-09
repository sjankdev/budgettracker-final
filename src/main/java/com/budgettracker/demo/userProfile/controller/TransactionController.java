package com.budgettracker.demo.userProfile.controller;

import com.budgettracker.demo.security.token.services.UserDetailsImpl;
import com.budgettracker.demo.userProfile.models.*;
import com.budgettracker.demo.userProfile.service.TransactionService;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    WalletService walletService;

    @GetMapping("/incomeTransaction/{id}")
    public String incomeTransaction(@PathVariable(value = "id") long id, Transaction transaction, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);


        model.addAttribute("transaction", transaction);
        model.addAttribute("incomeCategories", IncomeCategories.values());

        return "income_transaction";

    }

    @GetMapping("/expenseTransaction/{id}")
    public String expenseTransaction(@PathVariable(value = "id") long id, Transaction transaction, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);


        model.addAttribute("transaction", transaction);
        model.addAttribute("expenseCategories", ExpenseCategories.values());

        return "expense_transaction";

    }

    @PostMapping("/saveExpense/{walletId}")
    public String saveExpense(@PathVariable(value = "walletId") long walletId,
                              @Valid Transaction transaction, BindingResult result) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        Wallet wallet = walletService.getWalletById(walletId);

        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            return "expense_transaction";
        }

        transaction.setWallet(wallet);
        transactionService.saveExpense(transaction, walletId, userId);
        return "redirect:/api/wallet/userWallet/balance/" + userId;


    }

    @PostMapping("/saveIncome/{walletId}")
    public String saveIncome(@PathVariable(value = "walletId") long walletId,
                             @Valid Transaction transaction, BindingResult result) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        Wallet wallet = walletService.getWalletById(walletId);

        boolean thereAreErrors = result.hasErrors();
        if (thereAreErrors) {
            return "income_transaction";
        }

        transaction.setWallet(wallet);
        transactionService.saveIncome(transaction, walletId, userId);
        return "redirect:/api/wallet/userWallet/balance/" + userId;
    }


}
