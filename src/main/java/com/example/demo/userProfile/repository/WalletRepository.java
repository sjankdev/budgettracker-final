package com.example.demo.userProfile.repository;

import com.example.demo.userProfile.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByUserIdAndWalletName(Long userId, String walletName);

    Wallet findByUserId(Long userId);


}
