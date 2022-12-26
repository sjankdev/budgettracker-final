package com.budgettracker.demo.userProfile.models;

import org.hibernate.annotations.OnDelete;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private double amount;

    private String note;

    @DateTimeFormat(pattern = "mm-DD-yyyy")
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "category_name", referencedColumnName = "category_name")
    private Category category;

    @ManyToOne
    @JoinColumn(name="wallet_id", nullable=false)
    private Wallet wallet;


    public Transaction() {
    }

    public Transaction(double amount, String note, Date date, Category category) {
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.category = category;
    }

    @Column(name = "wallet_ids", nullable = false)
    private Long walletId;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }
}
