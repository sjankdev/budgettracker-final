package com.budgettracker.demo.userProfile.models;

import java.time.LocalDate;
import java.util.List;

public class TransactionGroup {
        private LocalDate date;
        private List<Transaction> transactions;

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
        }

    @Override
    public String toString() {
        return "TransactionGroup{" +
                "date=" + date +
                ", transactions=" + transactions +
                '}';
    }
}
