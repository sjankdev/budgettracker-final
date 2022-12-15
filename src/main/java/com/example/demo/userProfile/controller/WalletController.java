package com.example.demo.userProfile.controller;


import com.example.demo.userProfile.models.Wallet;
import com.example.demo.userProfile.repository.WalletRepository;
import com.example.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/tutorials/{walletId}")
    public ResponseEntity<Wallet> updateWallet(@PathVariable("walletId") long walletId, @RequestBody Wallet wallet) {
        Wallet _wallet = walletRepository.findById(walletId).orElseThrow(() -> new IllegalArgumentException("Not found Tutorial with id = " + walletId));

        _wallet.setWalletName(wallet.getWalletName());
        _wallet.setInitialBalance(wallet.getInitialBalance());

        return new ResponseEntity<>(walletRepository.save(_wallet), HttpStatus.OK);
    }

    @DeleteMapping("/wallet/{id}")
    public ResponseEntity<HttpStatus> deleteWallet(@PathVariable("id") long id) {
        walletRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/userWallet/{user_id}/balance")
    public String getUserWallet(@PathVariable(value = "user_id") Long user_id, Model model) {
        model.addAttribute("wallet", walletService.findByUserId(user_id));
        return "userProfile";
    }


}
