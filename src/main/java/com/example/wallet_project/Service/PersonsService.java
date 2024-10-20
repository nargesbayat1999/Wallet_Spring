package com.example.wallet_project.Service;

import com.example.wallet_project.ENUM.Gender;
import com.example.wallet_project.Entity.Persons;
import com.example.wallet_project.Repository.PersonsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonsService {

    @Autowired
    private PersonsRepository personsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public List<Persons> getAllPersons() {
        return personsRepository.findAll();
    }


    public Optional<Persons> getPersonById(int id) {
        return personsRepository.findById(id);
    }


    @Transactional
    public Persons updatePerson(int id, Persons personDetails) {
        Optional<Persons> person = personsRepository.findById(id);
        if (person.isPresent()) {
            Persons existingPerson = person.get();
            existingPerson.setName(personDetails.getName());
            existingPerson.setFamily(personDetails.getFamily());
            existingPerson.setNationalCode(personDetails.getNationalCode());
            existingPerson.setGender(personDetails.getGender());
            existingPerson.setMobile(personDetails.getMobile());
            existingPerson.setMilitaryStatus(personDetails.isMilitaryStatus());
            existingPerson.setEmail(personDetails.getEmail());
            return personsRepository.save(existingPerson);
        }
        return null;
    }


    @Transactional
    public void deletePerson(int id) {
        personsRepository.deleteById(id);
    }


    @Transactional
    public void registerUser(Persons person) {

        int age = calculateAge(person.getDateOfBirthDay());


        if (person.getGender() == Gender.MAN && age >= 18) {
            if (!person.isMilitaryStatus()) {
                throw new IllegalArgumentException("Military status is required for men over 18.");
            }
        }


        person.setPassword(passwordEncoder.encode(person.getPassword()));


        personsRepository.save(person);
    }

    private int calculateAge(Date birthDate) {
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }


    public void migratePasswords() {
        List<Persons> allPersons = personsRepository.findAll();
        for (Persons person : allPersons) {
            if (!person.getPassword().startsWith("$2a$")) {
                String encodedPassword = passwordEncoder.encode(person.getPassword());
                person.setPassword(encodedPassword);
                personsRepository.save(person);
            }
        }
    }
}

