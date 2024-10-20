package com.example.wallet_project.Controller;

import com.example.wallet_project.Entity.Account;
import com.example.wallet_project.Entity.Invoice;
import com.example.wallet_project.Repository.InvoiceRepository;
import com.example.wallet_project.Security.JwtUtil;
import com.example.wallet_project.Repository.AccountRepository;
import com.example.wallet_project.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;


    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable int id, @RequestBody Account accountDetails) {
        try {
            Account updatedAccount = accountService.updateAccount(id, accountDetails);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Invoice>> getUserTransactions(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        System.out.println("Username: " + username);
        List<Invoice> invoices = invoiceRepository.findByAccountPersonUsername(username);
        System.out.println("Invoices: " + invoices);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping
    public List<Account> getAccounts(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return accountRepository.findByPersonUsername(username);
    }
}
