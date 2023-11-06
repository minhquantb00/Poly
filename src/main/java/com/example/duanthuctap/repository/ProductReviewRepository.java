package com.example.duanthuctap.repository;

import com.example.duanthuctap.dto.response.ProductReviewResponse;
import com.example.duanthuctap.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {

    @Query("SELECT new com.example.duanthuctap.dto.response.ProductReviewResponse" +
            "(pr.contentRated, pr.pointEvaluation, pr.contentSeen) " +
            "FROM ProductReview pr")
    List<ProductReviewResponse> getAll();
}
