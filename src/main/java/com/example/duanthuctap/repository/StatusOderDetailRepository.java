package com.example.duanthuctap.repository;

import com.example.duanthuctap.entity.OrderStatus;
import com.example.duanthuctap.entity.StatusOderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusOderDetailRepository extends JpaRepository<StatusOderDetail, Integer> {

    Optional<StatusOderDetail> findByStatus(String name);
}
