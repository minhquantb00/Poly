package com.example.duanthuctap.service.impl;

import com.example.duanthuctap.dto.request.CartRequest;
import com.example.duanthuctap.dto.response.CartResponse;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.entity.CartItem;
import com.example.duanthuctap.entity.Carts;
import com.example.duanthuctap.entity.Product;
import com.example.duanthuctap.repository.CartItemRepository;
import com.example.duanthuctap.repository.CartsRepository;
import com.example.duanthuctap.repository.ProductRepository;
import com.example.duanthuctap.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public MessageResponse createCart() {
        Carts carts = new Carts();
        carts.setCreatedAt(LocalDate.now());
        cartsRepository.save(carts);
        return MessageResponse.builder().message("Tạo giỏ hàng thành công").build();
    }

    @Override
    public MessageResponse createCartItem(CartRequest cartRequest) {
        Optional<Carts> carts = cartsRepository.findById(cartRequest.getIdCart());
        if (carts.isEmpty()) {
            return MessageResponse.builder().message("Cart null").build();
        }
        CartItem cartItem = cartItemRepository.findByCartsAndProduct_ProductId(carts.get(), cartRequest.getIdProduct());
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + cartRequest.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(cartRequest.getQuantity());
            cartItem.setCarts(carts.get());
            cartItem.setStatus(1);
            Product product = new Product();
            product.setProductId(cartRequest.getIdProduct());
            cartItem.setProduct(product);
        }
        cartItemRepository.save(cartItem);
        return MessageResponse.builder().message("Thêm sản phẩm thành công").build();
    }

    @Override
    public List<CartResponse> showCart(Integer id) {
        List<Object[]> listObject = cartsRepository.showCart(id);
        List<CartResponse> cartResponseList = listObject.stream().map(objects -> new CartResponse(
                (String) objects[0],
                (String) objects[1],
                (Integer) objects[2],
                (BigDecimal) objects[3], // unitPrice
                ((BigDecimal) objects[3]).multiply(new BigDecimal((Integer) objects[2]))
        )).collect(Collectors.toList());
        return cartResponseList;
    }

    @Override
    public MessageResponse updateQuantityCartItem(Integer quantity, Integer idCartItem) {
        Optional<CartItem> cartItem = cartItemRepository.findById(idCartItem);
        if (cartItem.isEmpty()) {
            return MessageResponse.builder().message("CartItem is null").build();
        }
        cartItem.get().setQuantity(quantity);
        cartItem.get().setUpdateAt(LocalDate.now());
        cartItemRepository.save(cartItem.get());
        return MessageResponse.builder().message("Update lại số lượng thành công").build();
    }
}
