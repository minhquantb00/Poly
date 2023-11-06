package com.example.duanthuctap.service.impl;

import com.example.duanthuctap.dto.request.ProductReviewRequest;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.dto.response.NumberViewProduct;
import com.example.duanthuctap.dto.response.ProductReviewResponse;
import com.example.duanthuctap.entity.Product;
import com.example.duanthuctap.entity.ProductReview;
import com.example.duanthuctap.repository.ProductRepository;
import com.example.duanthuctap.repository.ProductReviewRepository;
import com.example.duanthuctap.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public MessageResponse createProductReview(ProductReviewRequest productReviewRequest, Integer idProduct) {
        Optional<Product> findByProduct = productRepository.findById(idProduct);
        if (findByProduct.isEmpty()) {
            return MessageResponse.builder().message("Sản phẩm không tồn tại").build();
        }
        ProductReview productReview = new ProductReview();
        productReview.setContentRated(productReviewRequest.getContentRated());
        productReview.setPointEvaluation(productReviewRequest.getPointEvaluation());
        productReview.setContentSeen(productReviewRequest.getContentSeen());
        productReview.setStatus(1);
        productReview.setProduct(findByProduct.get());
        productReviewRepository.save(productReview);
        return MessageResponse.builder().message("Bạn Đã Đánh Giá Sản Phẩm " + productReviewRequest.getPointEvaluation() + " " + "Sao").build();
    }

    @Override
    public NumberViewProduct findAll(String name) {
        return productRepository.showNumberViewProduct(name);
    }

    @Override
    public List<ProductReviewResponse> getAll() {
        return productReviewRepository.getAll();
    }
}
