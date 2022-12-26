package com.budgettracker.demo.userProfile.controller;

import com.budgettracker.demo.security.token.jwt.CurrentUserUtility;
import com.budgettracker.demo.security.token.services.UserDetailsImpl;
import com.budgettracker.demo.userProfile.models.Transaction;
import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.TransactionService;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    WalletRepository walletRepository;

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

/*
        Wallet wallet = walletService.getWalletById(id);
*/

        model.addAttribute("transaction", transaction);
        return "new_transaction";

    }

    @PostMapping("/saveTransaction")
    public String saveTransaction(@ModelAttribute("transaction") Transaction transaction) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        transaction.setWalletId(CurrentUserUtility.getCurrentUser().getId());
        transactionService.saveTransaction(transaction);
        return "redirect:/api/wallet/userWallet/balance/" + userId;
    }

}
