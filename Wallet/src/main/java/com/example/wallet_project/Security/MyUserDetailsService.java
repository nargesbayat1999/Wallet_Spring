package com.example.wallet_project.Security;

import com.example.wallet_project.Entity.Persons;
import com.example.wallet_project.Repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonsRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Persons person = personRepository.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                person.getUsername(),
                person.getPassword(),
                new ArrayList<>()
        );
    }
}
