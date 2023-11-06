package com.example.duanthuctap.service;

import com.example.duanthuctap.dto.request.ProductReviewRequest;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.dto.response.NumberViewProduct;
import com.example.duanthuctap.dto.response.ProductReviewResponse;

import java.util.List;

public interface ProductReviewService {

    MessageResponse createProductReview(ProductReviewRequest productReviewRequest, Integer idProduct);

    NumberViewProduct findAll(String name);

    List<ProductReviewResponse> getAll();
}
