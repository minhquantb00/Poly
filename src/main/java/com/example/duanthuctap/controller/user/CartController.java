package com.example.duanthuctap.controller.user;

import com.example.duanthuctap.dto.request.CartRequest;
import com.example.duanthuctap.dto.response.CartResponse;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart/")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping("show/{id}")
    public ResponseEntity<List<CartResponse>> showCart(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(cartService.showCart(id), HttpStatus.OK);
    }

    @PostMapping("tao-gio-hang")
    public ResponseEntity<MessageResponse> createCart() {
        return new ResponseEntity<>(cartService.createCart(), HttpStatus.CREATED);
    }

    @PostMapping("create-cart-detail")
    public ResponseEntity<MessageResponse> createCartItem(@RequestBody CartRequest cartRequest) {
        return new ResponseEntity<>(cartService.createCartItem(cartRequest), HttpStatus.CREATED);
    }

    @PutMapping("update-quantity-cart")
    public ResponseEntity<MessageResponse> updateQuantityCartItem(
            @RequestParam(name = "quantity") Integer quantity,
            @RequestParam(name = "idCartItem") Integer idCartItem) {
        return new ResponseEntity<>(cartService.updateQuantityCartItem(quantity, idCartItem), HttpStatus.OK);
    }
}
