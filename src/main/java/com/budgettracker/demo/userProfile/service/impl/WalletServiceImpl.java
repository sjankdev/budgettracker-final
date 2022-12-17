package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.List;

@Service
@Component
@EnableAutoConfiguration
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public List<Wallet> findDistinctIdByUserId(Long userId) {
        return walletRepository.findDistinctIdByUserId(userId);
    }

    @Override
    public void deleteWalletById(Long id) {
        this.walletRepository.deleteById(id);
    }

    @Override
    public Wallet getWalletById(Long id) {
        Optional<Wallet> optional = walletRepository.findById(id);
        Wallet wallet = null;
        if (optional.isPresent()) {
            wallet = optional.get();
        } else {
            throw new RuntimeException(" Wallet not found for id :: " + id);
        }
        return wallet;
    }

    @Override
    public void saveWallet(Wallet wallet) {
        this.walletRepository.save(wallet);
    }
}
