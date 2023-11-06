package com.example.duanthuctap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductResponse {

    private Integer productId;

    private String image;

    private String productName;

    private String title;

    private BigDecimal price;

    private String size;

    private String role;

    private String typeProduct;
}
