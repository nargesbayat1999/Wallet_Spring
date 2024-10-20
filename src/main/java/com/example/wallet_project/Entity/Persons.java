package com.example.wallet_project.Entity;


import com.example.wallet_project.ENUM.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;


@Entity
public class Persons {

    @OneToOne(mappedBy = "person")
    private Account account;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;


    private String family;


    @NotNull(message = "The DateOfBirthDay cannot be empty")
    Date DateOfBirthDay;


    @NotBlank(message = "The national code cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "National code must be exactly 10 digits")
    @Column(unique = true)
    private String nationalCode;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "The gender cannot be empty")
    private Gender gender;

    @Column(unique = true)
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{11}", message = "Mobile number must be exactly 11 digits")
    private String mobile;


    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;


    private boolean militaryStatus;


    @Column(unique = true)
    private String username;

    private String password;

//  Setters and Getters

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setFamily(String family) {
        this.family = family;
    }

    public String getFamily() {
        return family;
    }


    public void setDateOfBirthDay(Date dateOfBirthDay) {
        DateOfBirthDay = dateOfBirthDay;
    }

    public Date getDateOfBirthDay() {
        return DateOfBirthDay;
    }


    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }


    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setMilitaryStatus(boolean militaryStatus) {
        this.militaryStatus = militaryStatus;
    }

    public boolean isMilitaryStatus() {
        return militaryStatus;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }


}
