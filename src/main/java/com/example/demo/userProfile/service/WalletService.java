package com.example.demo.userProfile.service;

import com.example.demo.userProfile.models.Wallet;

public interface WalletService {

    Wallet findByUserId(Long userId);

}
