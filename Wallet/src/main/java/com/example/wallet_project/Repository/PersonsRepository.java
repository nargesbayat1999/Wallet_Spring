package com.example.wallet_project.Repository;

import com.example.wallet_project.Entity.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonsRepository extends JpaRepository<Persons, Integer> {


    Persons findByUsername(String username);
}
