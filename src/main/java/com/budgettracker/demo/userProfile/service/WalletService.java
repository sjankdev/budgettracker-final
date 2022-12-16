package com.budgettracker.demo.userProfile.service;

import com.budgettracker.demo.userProfile.models.Wallet;

public interface WalletService {

    Wallet findByUserId(Long userId);

}
