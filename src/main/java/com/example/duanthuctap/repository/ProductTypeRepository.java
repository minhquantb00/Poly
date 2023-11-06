package com.example.duanthuctap.repository;

import com.example.duanthuctap.dto.response.TypeProductResponse;
import com.example.duanthuctap.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    @Query("SELECT new com.example.duanthuctap.dto.response.TypeProductResponse(pt.nameProductType) FROM ProductType pt")
    List<TypeProductResponse> getAllTypeProduct();
}
