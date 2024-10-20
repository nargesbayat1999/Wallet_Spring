
package com.example.wallet_project.Controller;

import com.example.wallet_project.Entity.Invoice;
import com.example.wallet_project.Security.JwtUtil;
import com.example.wallet_project.Repository.InvoiceRepository;
import com.example.wallet_project.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")

public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/deposit")
    public ResponseEntity<Object[]> depositToAccount(
            @RequestParam("id") int accountId,
            @RequestParam("amount") int depositAmount,
            @RequestParam("description") String transactionDescription) {
        Object[] result = invoiceService.depositToAccount(accountId, depositAmount, transactionDescription);

        return ResponseEntity.ok(result);
    }


    @PostMapping("/withdraw")
    public ResponseEntity<Object[]> withdrawFromAccount(
            @RequestParam("id") int accountId,
            @RequestParam("amount") int withdrawalAmount,
            @RequestParam("description") String transactionDescription) {

        Object[] result = invoiceService.withdrawFromAccount(accountId, withdrawalAmount, transactionDescription);
        return ResponseEntity.ok(result);
    }


    @GetMapping
    public List<Invoice> getInvoices(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        return invoiceRepository.findByAccountPersonUsername(username);
    }
}


