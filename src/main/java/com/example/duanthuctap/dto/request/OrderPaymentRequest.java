package com.example.duanthuctap.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentRequest {

    private Long totalPrice;

    private String fullName;

    private String phoneNumber;

    private String address;

    private String email;

    private List<Integer> cartList;

}
