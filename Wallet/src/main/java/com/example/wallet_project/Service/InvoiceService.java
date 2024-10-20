package com.example.wallet_project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    @Autowired
    private AccountService accountService;


    public Object[] depositToAccount(int id, int amount, String description) {

        Object[] response = accountService.deposit(id, amount, description);


        return response;
    }

    public Object[] withdrawFromAccount(int Id, int amount, String description) {

        Object[] response = accountService.withdraw(Id, amount, description);
        return response;
    }
}

