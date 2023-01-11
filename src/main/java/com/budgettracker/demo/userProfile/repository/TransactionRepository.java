package com.budgettracker.demo.userProfile.repository;

import com.budgettracker.demo.userProfile.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
