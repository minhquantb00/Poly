package com.example.duanthuctap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private Integer productTypeId;
    @Basic
    @Column(name = "name_product_type")
    private String nameProductType;
    @Basic
    @Column(name = "image_type_product")
    private String imageTypeProduct;
    @Basic
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Basic
    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
    private List<Product> productList;

}
