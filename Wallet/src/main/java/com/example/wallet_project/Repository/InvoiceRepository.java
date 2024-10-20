package com.example.wallet_project.Repository;

import com.example.wallet_project.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {


    List<Invoice> findByAccountPersonUsername(String username);

}

