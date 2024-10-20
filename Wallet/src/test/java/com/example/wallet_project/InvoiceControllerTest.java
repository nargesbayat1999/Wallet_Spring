package com.example.wallet_project;

import com.example.wallet_project.Controller.InvoiceController;
import com.example.wallet_project.Entity.Invoice;
import com.example.wallet_project.Security.JwtUtil;
import com.example.wallet_project.Repository.InvoiceRepository;
import com.example.wallet_project.Service.InvoiceService;
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
import static org.mockito.Mockito.*;

class InvoiceControllerTest {

    @InjectMocks
    private InvoiceController invoiceController;

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void depositToAccount_ShouldReturnResult() {
        int accountId = 1;
        int depositAmount = 1000;
        String transactionDescription = "Deposit for testing";
        Object[] expectedResult = new Object[] {"Success", 1000};

        when(invoiceService.depositToAccount(accountId, depositAmount, transactionDescription)).thenReturn(expectedResult);

        ResponseEntity<Object[]> response = invoiceController.depositToAccount(accountId, depositAmount, transactionDescription);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(expectedResult, response.getBody());
    }

    @Test
    void withdrawFromAccount_ShouldReturnResult() {
        int accountId = 1;
        int withdrawalAmount = 500;
        String transactionDescription = "Withdrawal for testing";
        Object[] expectedResult = new Object[] {"Success", 500};

        when(invoiceService.withdrawFromAccount(accountId, withdrawalAmount, transactionDescription)).thenReturn(expectedResult);

        ResponseEntity<Object[]> response = invoiceController.withdrawFromAccount(accountId, withdrawalAmount, transactionDescription);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(expectedResult, response.getBody());
    }

    @Test
    void getInvoices_ShouldReturnInvoices() {
        String token = "Bearer token";
        String username = "testUser";
        Invoice invoice = new Invoice();
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);

        when(jwtUtil.extractUsername(token.substring(7))).thenReturn(username);
        when(invoiceRepository.findByAccountPersonUsername(username)).thenReturn(invoices);

        List<Invoice> response = invoiceController.getInvoices(token);

        assertEquals(invoices, response);
    }
}
