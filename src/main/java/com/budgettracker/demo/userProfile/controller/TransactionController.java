package com.budgettracker.demo.userProfile.controller;

import com.budgettracker.demo.security.token.jwt.CurrentUserUtility;
import com.budgettracker.demo.security.token.services.UserDetailsImpl;
import com.budgettracker.demo.userProfile.models.Category;
import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.CategoryRepository;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.CategoryService;
import com.budgettracker.demo.userProfile.service.TransactionService;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    WalletService walletService;

    @Autowired
    CategoryService categoryService;


    @GetMapping("/showNewTransactionForm/{id}")
    public String showNewTransactionForm(@PathVariable(value = "id") long id, Transaction transaction, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);

        model.addAttribute("transaction", transaction);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "new_transaction";

    }

    @PostMapping("/saveTransaction/{walletId}")
    public String saveTransaction(@PathVariable(value = "walletId") long walletId,
                                  @ModelAttribute("wallets") Transaction transaction, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        Wallet wallet = walletService.getWalletById(walletId);

        double amount = transaction.getAmount();

/*
        wallet.setTotalBalance(transactionService.netWorthAfterOutcomeTransaction(walletId, userId, amount));
*/
        transaction.setWallet(wallet);
        transactionService.saveTransaction(transaction, walletId, userId, amount);
        return "redirect:/api/wallet/userWallet/balance/" + userId;
    }

}
