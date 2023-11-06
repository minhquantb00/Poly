package com.example.duanthuctap.repository;

import com.example.duanthuctap.entity.Decentralization;
import com.example.duanthuctap.enums.VaiTroEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DecentralizationRepository extends JpaRepository<Decentralization, Integer> {

    Optional<Decentralization> findByAuthorityName(VaiTroEnums name);
}
