package com.budgettracker.demo.userProfile.repository;

import com.budgettracker.demo.userProfile.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findDistinctIdByUserId(Long userId);


}
