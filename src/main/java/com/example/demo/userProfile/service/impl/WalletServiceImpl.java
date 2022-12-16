package com.example.demo.userProfile.service.impl;

import com.example.demo.userProfile.models.Wallet;
import com.example.demo.userProfile.repository.WalletRepository;
import com.example.demo.userProfile.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@EnableAutoConfiguration
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet findByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }
}
