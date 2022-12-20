package com.budgettracker.demo.userProfile.models;

import com.budgettracker.demo.security.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please, insert a wallet name")
    private String walletName;

    private double initialBalance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "user_id", nullable = false)
    private Long userId;


    public Wallet() {
    }

    public Wallet(String walletName, double initialBalance) {
        this.walletName = walletName;
        this.initialBalance = initialBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
