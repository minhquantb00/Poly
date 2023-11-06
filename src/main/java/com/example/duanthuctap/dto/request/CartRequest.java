package com.example.duanthuctap.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    private Integer idCart;

    private Integer idProduct;

    private Integer quantity;

}
