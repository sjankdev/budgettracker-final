package com.budgettracker.demo.userProfile.models;

import org.hibernate.annotations.OnDelete;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "transaction")//course
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private double amount;

    private String note;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", columnDefinition = "ENUM('EXPENSE', 'INCOME')")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Nullable
    @Column(name = "expense_categories", columnDefinition = "ENUM('FOOD_AND_DRINK', 'SHOPPING', 'TRANSPORT', 'HOME'," +
            " 'BILLS_AND_FEES', 'ENTERTAINMENT', 'CAR', 'TRAVEL', 'FAMILY_AND_PERSONAL', 'HEALTHCARE'," +
            " 'EDUCATION', 'GROCERIES', 'GIFTS', 'BEAUTY', 'WORK', 'SPORTS_AND_HOBBIES', 'OTHER')")
    private ExpenseCategories expenseCategories;

    @Nullable
    @Enumerated(EnumType.STRING)
    @Column(name = "income_categories", columnDefinition = "ENUM('SALARY', 'BUSINESS', 'GIFTS', 'EXTRA_INCOME', 'LOAN', 'PARENTAL_LEAVE', 'INSURANCE_PAYOUT', 'OTHER')")
    private IncomeCategories incomeCategories;

    public Transaction() {
    }

    public Transaction(double amount, String note, Date date, ExpenseCategories expenseCategories, IncomeCategories incomeCategories) {
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.expenseCategories = expenseCategories;
        this.incomeCategories = incomeCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public ExpenseCategories getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(ExpenseCategories expenseCategories) {
        this.expenseCategories = expenseCategories;
    }

    public IncomeCategories getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(IncomeCategories incomeCategories) {
        this.incomeCategories = incomeCategories;
    }
}
