package com.budgettracker.demo.userProfile.repository;

import com.budgettracker.demo.userProfile.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByUserIdAndWalletName(Long userId, String walletName);

    List<Wallet> findDistinctIdByUserId(Long userId);


}
