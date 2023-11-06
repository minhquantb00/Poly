package com.example.duanthuctap.controller.user;

import com.example.duanthuctap.dto.request.ProductReviewRequest;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.dto.response.NumberViewProduct;
import com.example.duanthuctap.dto.response.ProductReviewResponse;
import com.example.duanthuctap.service.impl.ProductReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-review/")
public class ProductReviewController {

    @Autowired
    private ProductReviewServiceImpl productReviewService;

    @PostMapping("create/{idProduct}")
    public ResponseEntity<MessageResponse> createProductReview(@RequestBody ProductReviewRequest productReviewRequest,
                                                               @PathVariable("idProduct") Integer idProduct) {
        return new ResponseEntity<>(
                productReviewService.createProductReview(productReviewRequest, idProduct),
                HttpStatus.CREATED);
    }

    @GetMapping("show-number_view_product")
    public ResponseEntity<NumberViewProduct> showNumberViewProduct(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(productReviewService.findAll(name), HttpStatus.OK);
    }

    @GetMapping("show-product-review")
    public ResponseEntity<List<ProductReviewResponse>> getAll() {
        return new ResponseEntity<>(productReviewService.getAll(), HttpStatus.OK);
    }
}
