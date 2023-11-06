package com.example.duanthuctap.service;

import com.example.duanthuctap.dto.response.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {

    List<ListProductResponse> listProductResponses(Integer pageNumber, Integer pageSize);

    DetailProductResponse findAllDetailProduct(Integer id);

    List<SanPhamLienQuanResponse> sanPhamLienQuan(String nameProductType, String nameSole, String size);

}
