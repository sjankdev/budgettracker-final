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


    @GetMapping("/showNewTransactionForm/{id}")
    public String showNewTransactionForm(@PathVariable(value = "id") long id, Transaction transaction, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);


        model.addAttribute("transaction", transaction);
        model.addAttribute("transactionType", TransactionType.values());
        model.addAttribute("expenseCategories", ExpenseCategories.values());
        model.addAttribute("incomeCategories", IncomeCategories.values());

        return "new_transaction";

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
            return "new_transaction";
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
            return "new_transaction";
        }

        transaction.setWallet(wallet);
        transactionService.saveIncome(transaction, walletId, userId);
        return "redirect:/api/wallet/userWallet/balance/" + userId;
    }


}
