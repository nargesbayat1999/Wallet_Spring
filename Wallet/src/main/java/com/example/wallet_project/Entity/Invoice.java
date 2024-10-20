package com.example.wallet_project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Invoice {

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private LocalDateTime transactionDate;


    private int accountBalance;


    private String descriptionOfTransaction;


    private String status;


    private String type;

// Getters and Setters

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }


    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getAccountBalance() {
        return accountBalance;
    }


    public void setDescriptionOfTransaction(String descriptionOfTransaction) {
        this.descriptionOfTransaction = descriptionOfTransaction;
    }

    public String getDescriptionOfTransaction() {
        return descriptionOfTransaction;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }


}

