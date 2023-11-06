package com.example.duanthuctap.repository;

import com.example.duanthuctap.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByPaymentMethod(String transaction);
}
