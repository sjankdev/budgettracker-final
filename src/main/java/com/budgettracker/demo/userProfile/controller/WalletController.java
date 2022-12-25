package com.budgettracker.demo.userProfile.controller;

import com.budgettracker.demo.security.payload.response.MessageResponse;
import com.budgettracker.demo.security.repository.UserRepository;
import com.budgettracker.demo.security.token.jwt.CurrentUserUtility;
import com.budgettracker.demo.security.token.services.UserDetailsImpl;
import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

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
    public String getUserWallet(@PathVariable(value = "user_id") Long user_id, Model model) {
        model.addAttribute("wallet", walletService.findDistinctIdByUserId(user_id));
        model.addAttribute("wallets", walletService.netWorth(user_id));
        return "user_profile";
    }

    @PostMapping("/saveWallet")
    public String saveWallet(@ModelAttribute("wallet") Wallet wallet) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        long userId = user.getId();

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
