package com.example.duanthuctap.repository;

import com.example.duanthuctap.dto.response.*;
import com.example.duanthuctap.entity.CartItem;
import com.example.duanthuctap.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.example.duanthuctap.dto.response.ListProductResponse" +
            "(p.productId, i.imageProduct,p.nameProduct, p.price) " +
            "FROM Product p " +
            "JOIN p.productImageList i " +
            "WHERE i.status = 1")
    Page<ListProductResponse> findAllProduct(Pageable pageable);

    @Query("SELECT new com.example.duanthuctap.dto.response.DetailProductResponse" +
            "(p.productId, i.imageProduct,p.nameProduct, p.title, p.price, s.size, sole.nameSole, pt.nameProductType) " +
            "FROM Product p JOIN p.soleList sole JOIN p.sizeList s JOIN p.productType pt " +
            "JOIN p.productImageList i WHERE p.productId = :id")
    DetailProductResponse findAllDetailProduct(@Param("id") Integer id);

    @Query("SELECT new com.example.duanthuctap.dto.response.SanPhamLienQuanResponse" +
            "(i.imageProduct,p.nameProduct, p.price) " +
            "FROM Product p " +
            "JOIN p.productImageList i " +
            "JOIN p.productType pt " +
            "JOIN p.soleList sl " +
            "JOIN p.sizeList s " +
            "WHERE i.status = 1 " +
            "AND pt.nameProductType = :nameProductType " +
            "AND sl.nameSole = :nameSole " +
            "AND s.size = :size")
    List<SanPhamLienQuanResponse> sanPhamLienQuan
            (@Param("nameProductType") String nameProductType,
             @Param("nameSole") String nameSole,
             @Param("size") String size);

    @Query("SELECT new com.example.duanthuctap.dto.response.NumberViewProduct(p.numberOfViews) " +
            "FROM Product p " +
            "WHERE p.nameProduct = :name")
    NumberViewProduct showNumberViewProduct(@Param("name") String name);

}
