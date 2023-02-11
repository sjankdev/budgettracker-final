package com.budgettracker.demo.userProfile.repository;

import com.budgettracker.demo.userProfile.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findDistinctIdByUserId(Long userId);

    @Query( value="select * from transaction where user_id = ?1 order by date asc", nativeQuery=true)
    List<Transaction> getTransactionsByUserId(Long userId);

}
