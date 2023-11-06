package com.example.duanthuctap.controller.user;

import com.example.duanthuctap.dto.response.TypeProductResponse;
import com.example.duanthuctap.service.impl.TypeProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type-product/")
public class ProductTypeController {

    @Autowired
    private TypeProductServiceImpl productService;

    @GetMapping("show")
    public ResponseEntity<List<TypeProductResponse>> findAllProduct() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
}
