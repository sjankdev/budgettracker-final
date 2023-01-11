package com.budgettracker.demo.userProfile.repository;

import com.budgettracker.demo.userProfile.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    long existsByUserIdAndWalletName(Long userId, Wallet wallet);

    List<Wallet> findDistinctIdByUserId(Long userId);


}
