package com.example.wallet_project;

import com.example.wallet_project.Controller.AccountController;
import com.example.wallet_project.Entity.Account;
import com.example.wallet_project.Entity.Invoice;
import com.example.wallet_project.Repository.InvoiceRepository;
import com.example.wallet_project.Security.JwtUtil;
import com.example.wallet_project.Repository.AccountRepository;
import com.example.wallet_project.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_ShouldReturnCreatedAccount() {
        Account account = new Account();
        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    void updateAccount_ShouldReturnUpdatedAccount() {
        int accountId = 1;
        Account accountDetails = new Account();
        Account updatedAccount = new Account();
        when(accountService.updateAccount(eq(accountId), any(Account.class))).thenReturn(updatedAccount);

        ResponseEntity<Account> response = accountController.updateAccount(accountId, accountDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAccount, response.getBody());
    }

    @Test
    void updateAccount_ShouldReturnNotFound_WhenAccountDoesNotExist() {
        int accountId = 1;
        Account accountDetails = new Account();
        when(accountService.updateAccount(eq(accountId), any(Account.class))).thenThrow(new RuntimeException());

        ResponseEntity<Account> response = accountController.updateAccount(accountId, accountDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteAccount_ShouldReturnNoContent() {
        int accountId = 1;

        ResponseEntity<Void> response = accountController.deleteAccount(accountId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(accountService, times(1)).deleteAccount(accountId);
    }

    @Test
    void getUserTransactions_ShouldReturnInvoices() {
        String token = "Bearer token";
        String username = "testUser";
        Invoice invoice = new Invoice();
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);

        when(jwtUtil.extractUsername(token.substring(7))).thenReturn(username);
        when(invoiceRepository.findByAccountPersonUsername(username)).thenReturn(invoices);

        ResponseEntity<List<Invoice>> response = accountController.getUserTransactions(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(invoices, response.getBody());
    }

    @Test
    void getAccounts_ShouldReturnAccounts() {
        String token = "Bearer token";
        String username = "testUser";
        Account account = new Account();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        when(jwtUtil.extractUsername(token.substring(7))).thenReturn(username);
        when(accountRepository.findByPersonUsername(username)).thenReturn(accounts);

        List<Account> response = accountController.getAccounts(token);

        assertEquals(accounts, response);
    }
}

