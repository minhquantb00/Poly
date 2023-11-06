package com.example.duanthuctap.service.impl;

import com.example.duanthuctap.dto.response.*;
import com.example.duanthuctap.repository.ProductRepository;
import com.example.duanthuctap.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ListProductResponse> listProductResponses(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ListProductResponse> listProductResponses = productRepository.findAllProduct(pageable);
        return listProductResponses.getContent();
    }

    @Override
    public DetailProductResponse findAllDetailProduct(Integer id) {
        return productRepository.findAllDetailProduct(id);
    }

    @Override
    public List<SanPhamLienQuanResponse> sanPhamLienQuan(String nameProductType, String nameSole, String size) {
        return productRepository.sanPhamLienQuan(nameProductType, nameSole, size);
    }

}
