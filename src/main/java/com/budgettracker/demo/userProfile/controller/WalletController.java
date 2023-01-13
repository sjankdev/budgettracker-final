package com.budgettracker.demo.userProfile.controller;

import com.budgettracker.demo.security.token.jwt.CurrentUserUtility;
import com.budgettracker.demo.security.token.services.UserDetailsImpl;
import com.budgettracker.demo.userProfile.models.Wallet;
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


@Controller
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;


    @GetMapping("/showNewWalletForm")
    public String showNewWalletForm(Wallet wallet, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);
        return "new_wallet";
    }

    @GetMapping("/userWallet/balance/{user_id}")
    @PreAuthorize("#user_id == authentication.principal.id")
    public String getUserWallet(@PathVariable(value = "user_id") Long user_id, Model model) {
        model.addAttribute("wallet", walletService.findDistinctIdByUserId(user_id));
        model.addAttribute("wallets", walletService.netWorth(user_id));
        return "user_profile";
    }

    @PostMapping("/saveWallet")
    public String saveWallet(@Valid @ModelAttribute("wallet") Wallet wallet, BindingResult result, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        if (result.hasErrors()) {
            model.addAttribute("userId", userId);
            return "new_wallet";
        }


        wallet.setUserId(CurrentUserUtility.getCurrentUser().getId());
        walletService.saveWallet(wallet);
        return "redirect:/api/wallet/userWallet/balance/" + userId;
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();
        model.addAttribute("userId", userId);

        Wallet wallet = walletService.getWalletById(id);

        model.addAttribute("wallet", wallet);
        return "update_wallet";
    }

    @GetMapping("/delete/{id}")
    public String deleteWallet(@PathVariable("id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

        walletService.deleteWalletById(id);
        return "redirect:/api/wallet/userWallet/balance/" + userId;
    }
}
