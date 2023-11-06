package com.example.duanthuctap.repository;

import com.example.duanthuctap.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByUserName(String name);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByResetPasswordToken(String email);

}
