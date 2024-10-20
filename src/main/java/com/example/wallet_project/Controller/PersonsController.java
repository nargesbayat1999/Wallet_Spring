package com.example.wallet_project.Controller;

import com.example.wallet_project.Entity.Persons;
import com.example.wallet_project.Service.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    private PersonsService personsService;


    @PostMapping("/add")
    public ResponseEntity<Persons> registerUser(@RequestBody Persons person) {
        try {
            personsService.registerUser(person);
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Persons>> getAllPersons() {
        List<Persons> personsList = personsService.getAllPersons();
        return ResponseEntity.ok(personsList);
    }


    @PostMapping("/migratePasswords")
    public ResponseEntity<String> migratePasswords() {
        try {
            personsService.migratePasswords();
            return ResponseEntity.ok("Passwords migrated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to migrate passwords");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persons> updatePerson(@PathVariable int id, @RequestBody Persons personDetails) {
        Persons updatedPerson = personsService.updatePerson(id, personDetails);
        if (updatedPerson != null) {
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        personsService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}


