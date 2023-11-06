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
public class OrderResponse {

    private String codeOrder;

    private String nameCustomer;

    private String nameStaff;

    private LocalDate createDate;

    private Long totalPrice;

    private String status;
}
