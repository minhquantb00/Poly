package com.example.duanthuctap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailResponse {

    private String codeOrder;

    private Long totalPrice;

    private LocalDate updateDate;

    private String transaction;

    private String peopleConfirm;

    private String productName;

    private Integer quantity;

    private BigDecimal unitPrice;
}
