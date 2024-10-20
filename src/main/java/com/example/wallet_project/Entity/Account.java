package com.example.wallet_project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Account {

    @OneToOne
    @JoinColumn(name = "person_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Persons person;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Invoice> invoices;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private long AccountNumber;


    private long shabaNumber;


    private int accountBalance = 10000;


    private LocalDateTime accountCreationDate;

// Setters and Getters

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setAccountNumber(long accountNumber) {
        this.AccountNumber = accountNumber;
    }

    public long getAccountNumber() {
        return AccountNumber;
    }


    public void setShabaNumber(long shabaNumber) {
        this.shabaNumber = shabaNumber;
    }

    public long getShabaNumber() {
        return shabaNumber;
    }


    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getAccountBalance() {
        return accountBalance;
    }


    public void setAccountCreationDate(LocalDateTime accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDate;
    }


    public void setPerson(Persons person) {
        this.person = person;
    }

    public Persons getPerson() {
        return person;
    }


    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }


}
