package com.budgettracker.demo.userProfile.models;

import org.hibernate.annotations.OnDelete;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private double amount;

    private String note;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "category_name", referencedColumnName = "category_name")
    private Category category;

    @ManyToOne
    @JoinColumn(name="wallet_id", nullable=false)
    private Wallet wallet;

}
