package com.budgettracker.demo.userProfile.service;

import com.budgettracker.demo.userProfile.models.Wallet;

import java.util.List;

public interface WalletService {

    List<Wallet> findDistinctIdByUserId(Long userId);

    void deleteWalletById(Long id);

}
