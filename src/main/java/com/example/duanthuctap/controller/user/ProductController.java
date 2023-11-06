package com.example.duanthuctap.controller.user;

import com.example.duanthuctap.dto.response.*;
import com.example.duanthuctap.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("all")
    public ResponseEntity<List<ListProductResponse>> listResponseEntity(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize
    ) {
        return new ResponseEntity<>(productService.listProductResponses(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<DetailProductResponse> findAllDetailProduct(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(productService.findAllDetailProduct(id), HttpStatus.OK);
    }

    @GetMapping("san-pham-lien-quan")
    public ResponseEntity<List<SanPhamLienQuanResponse>> sanPhamLienQuan(
            @RequestParam(name = "nameProductType") String nameProductType,
            @RequestParam(name = "nameSole") String nameSole,
            @RequestParam(name = "size") String size
    ) {
        return new ResponseEntity<>(productService.sanPhamLienQuan(nameProductType, nameSole, size), HttpStatus.OK);
    }

}
