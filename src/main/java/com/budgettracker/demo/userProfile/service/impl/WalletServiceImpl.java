package com.budgettracker.demo.userProfile.service.impl;

import com.budgettracker.demo.userProfile.models.Wallet;
import com.budgettracker.demo.userProfile.repository.WalletRepository;
import com.budgettracker.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
}
