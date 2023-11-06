package com.example.duanthuctap.service.impl;

import com.example.duanthuctap.dto.response.TypeProductResponse;
import com.example.duanthuctap.repository.ProductTypeRepository;
import com.example.duanthuctap.service.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeProductServiceImpl implements TypeProductService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public List<TypeProductResponse> getAll() {
        return productTypeRepository.getAllTypeProduct();
    }
}
