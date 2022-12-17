package com.budgettracker.demo.userProfile.controller;


import com.budgettracker.demo.security.models.User;
import com.budgettracker.demo.security.payload.response.MessageResponse;
import com.budgettracker.demo.security.repository.UserRepository;
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

    @PostMapping("/user/{user_id}/wallets")
    public ResponseEntity<?> createWallet(@PathVariable(value = "user_id") Long user_id, @RequestBody Wallet walletRequest) {

        if (walletRepository.existsByUserIdAndWalletName(user_id, walletRequest.getWalletName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("You already have wallet with that name, choose another!"));
        }

        Wallet comment = userRepository.findById(user_id).map(tutorial -> {
            walletRequest.setUser(tutorial);
            return walletRepository.save(walletRequest);
        }).orElseThrow(() -> new IllegalArgumentException("Not found Tutorial with id = " + user_id));

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PostMapping("/saveWallet")
    public String saveWallet(@ModelAttribute("wallet") Wallet wallet) {
        // save employee to database
        walletService.saveWallet(wallet);
        return "redirect:/";
    }


    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get employee from the service
        Wallet wallet = walletService.getWalletById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("wallet", wallet);
        return "update_wallet";
    }

    @PostMapping("/delete/{id}")
    public String deleteWallet(@PathVariable("id") long id, Model model) {
        walletService.deleteWalletById(id);
        return "redirect:/";
    }


    @GetMapping("/userWallet/balance/{user_id}")
    public String getUserWallet(@PathVariable(value = "user_id") Long user_id, Model model) {
        model.addAttribute("wallet", walletService.findDistinctIdByUserId(user_id));
        return "userProfile";
    }


}
