package com.example.duanthuctap.controller.user;

import com.example.duanthuctap.dto.request.OrderPaymentRequest;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.service.impl.OrdersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-payment/")
public class OrderPaymentController {

    @Autowired
    private OrdersServiceImpl ordersService;

    @PostMapping("create-order")
    public ResponseEntity<MessageResponse> createOrder(@RequestBody OrderPaymentRequest orderPaymentRequest) {
        return new ResponseEntity<>(ordersService.orderPayment(orderPaymentRequest), HttpStatus.CREATED);
    }
}
