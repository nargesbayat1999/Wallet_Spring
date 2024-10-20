
package com.example.wallet_project.Service;

import com.example.wallet_project.Entity.Account;
import com.example.wallet_project.Entity.Invoice;
import com.example.wallet_project.Entity.Persons;
import com.example.wallet_project.Repository.AccountRepository;
import com.example.wallet_project.Repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;


    @Transactional
    public Account createAccount(Account account) {

        account.setAccountNumber(generateUniqueAccountNumber());
        account.setShabaNumber(generateUniqueShabaNumber());
        account.setAccountCreationDate(LocalDate.now().atStartOfDay());

        return accountRepository.save(account);
    }


    private long generateUniqueAccountNumber() {
        Random random = new Random();

        return 1000000000L + random.nextInt(900000000);
    }

    private long generateUniqueShabaNumber() {
        Random random = new Random();

        return 1000000000000000L + random.nextLong(9000000000000000L);
    }


    @Transactional
    public Object[] deposit(int accountId, int amount, String description) {

        Account account = accountRepository.findById(accountId)

                .orElseThrow(() -> new RuntimeException("Account not found"));


        Persons person = account.getPerson();


        account.setAccountBalance(account.getAccountBalance() + amount);
        accountRepository.save(account);


        Invoice invoice = createInvoice(account, amount, description, "deposit");
        return new Object[]{invoice, "persons FullName:" + person.getName() + " " + person.getFamily(), "AccountNumber:" + account.getAccountNumber()};

    }


    @Transactional
    public Object[] withdraw(int accountId, int amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));


        long dailyWithdrawn = getTotalWithdrawnLast24Hours(account);
        if (dailyWithdrawn + amount > 10000000) {
            throw new RuntimeException("Daily withdraw limit of 10 million exceeded");
        }


        if (account.getAccountBalance() < amount || account.getAccountBalance() <= 100000) {
            throw new RuntimeException("Insufficient balance");
        }


        account.setAccountBalance(account.getAccountBalance() - amount);
        accountRepository.save(account);
        Persons person = account.getPerson();
        Invoice invoice = createInvoice(account, amount, description, "withdraw");


        return new Object[]{invoice, "persons FullName:" + person.getName() + " " + person.getFamily(), "AccountNumber:" + account.getAccountNumber()};

    }

    private long getTotalWithdrawnLast24Hours(Account account) {
        LocalDateTime last24Hours = LocalDateTime.now().minusHours(24);
        return account.getInvoices().stream()
                .filter(invoice -> invoice.getType().equals("withdraw"))
                .filter(invoice -> invoice.getTransactionDate().isAfter(last24Hours))
                .mapToLong(Invoice::getAccountBalance)
                .sum();
    }


    @Transactional
    private Invoice createInvoice(Account account, int amount, String description, String type) {
        Invoice invoice = new Invoice();
        invoice.setAccount(account);
        invoice.setAccountBalance(amount);
        invoice.setDescriptionOfTransaction(description);
        invoice.setType(type);
        invoice.setTransactionDate(LocalDateTime.now());
        invoice.setStatus("completed");


        return invoiceRepository.save(invoice);
    }

    public Account getAccount(int accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<Invoice> getAccountTransactions(int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getInvoices();
    }


    @Transactional
    public void deleteAccount(int accountId) {

        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found");
        }

        accountRepository.deleteById(accountId);
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    public Account updateAccount(int id, Account accountDetails) {
        return accountRepository.findById(id)
                .map(existingAccount -> {
                    existingAccount.setAccountNumber(accountDetails.getAccountNumber());
                    existingAccount.setShabaNumber(accountDetails.getShabaNumber());
                    existingAccount.setAccountBalance(accountDetails.getAccountBalance());
                    existingAccount.setAccountCreationDate(accountDetails.getAccountCreationDate());
                    return accountRepository.save(existingAccount);
                })
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }


}

