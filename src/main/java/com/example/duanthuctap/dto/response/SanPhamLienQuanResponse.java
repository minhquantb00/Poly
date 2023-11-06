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
public class SanPhamLienQuanResponse {

    private String image;

    private String productName;

    private BigDecimal price;
}
