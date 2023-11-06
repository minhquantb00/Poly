package com.example.duanthuctap.service.impl;

import com.example.duanthuctap.entity.Account;
import com.example.duanthuctap.model.UserDetailsCustom;
import com.example.duanthuctap.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> findByUsername = accountRepository.findByUserName(username);
        return new UserDetailsCustom(findByUsername.get());
    }
}
