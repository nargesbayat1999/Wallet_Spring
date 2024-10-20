package com.example.wallet_project.Repository;

import com.example.wallet_project.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


    List<Account> findByPersonUsername(String username);
}
