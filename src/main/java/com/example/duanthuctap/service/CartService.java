package com.example.duanthuctap.service;

import com.example.duanthuctap.dto.request.CartRequest;
import com.example.duanthuctap.dto.response.CartResponse;
import com.example.duanthuctap.dto.response.MessageResponse;

import java.util.List;

public interface CartService {

    MessageResponse createCart();

    MessageResponse createCartItem(CartRequest cartRequest);

    List<CartResponse> showCart(Integer id);

    MessageResponse updateQuantityCartItem(Integer quantity, Integer idCartItem);
}
