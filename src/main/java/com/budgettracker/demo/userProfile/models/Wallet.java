package com.budgettracker.demo.userProfile.models;

import com.budgettracker.demo.security.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    private Long id;

    @NotBlank(message = "Please, insert a wallet name")
    private String walletName;

    @NotNull(message = "Please, insert a amount")
    private Double initialBalance;

    @Transient
    private double totalBalance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "wallet", cascade = {
            CascadeType.ALL})
    private Set<Transaction> transactions;


    public Wallet() {
    }

    public Wallet(String walletName, Double initialBalance) {
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

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
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

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

}
