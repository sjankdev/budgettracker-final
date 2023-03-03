package com.budgettracker.demo.userProfile.models;

import java.time.LocalDate;
import java.util.List;

public class TransactionGroup {
        private LocalDate date;
        private List<Transaction> transactions;
        private double incomeSum;
        private double expenseSum;
        private double monthBalance;

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

    public double getExpenseSum() {
        return expenseSum;
    }

    public double getMonthBalance() {
        return monthBalance;
    }

    public void setMonthBalance(double monthBalance) {
        this.monthBalance = monthBalance;
    }

    public double setExpenseSum(double expenseSum) {
        this.expenseSum = expenseSum;
        return expenseSum;
    }

    public double getIncomeSum() {
        return incomeSum;
    }

    public double setIncomeSum(double incomeSum) {
        this.incomeSum = incomeSum;
        return incomeSum;
    }

    @Override
    public String toString() {
        return "TransactionGroup{" +
                "date=" + date +
                ", transactions=" + transactions +
                '}';
    }
}
