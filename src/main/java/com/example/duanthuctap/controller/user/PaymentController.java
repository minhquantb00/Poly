package com.example.duanthuctap.controller.user;

import com.example.duanthuctap.dto.request.PaymentRequest;
import com.example.duanthuctap.dto.request.TransactionRequest;
import com.example.duanthuctap.dto.response.PaymentResponse;
import com.example.duanthuctap.dto.response.TransactionResponse;
import com.example.duanthuctap.entity.Payment;
import com.example.duanthuctap.service.impl.OrdersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/v1/payment/")
public class PaymentController {

    @Autowired
    private OrdersServiceImpl ordersService;

    @PostMapping("vn_pay")
    public ResponseEntity<PaymentResponse> transfer(
            HttpServletRequest req, @RequestBody TransactionRequest transactionRequest)
    {
        return ResponseEntity.ok(ordersService.callPaymentApi(req,transactionRequest));
    }

    @PostMapping("cash")
    public ResponseEntity<TransactionResponse> cash(@RequestBody TransactionRequest transactionRequest)
    {
        return ResponseEntity.ok(ordersService.payTransaction(transactionRequest));
    }

    @GetMapping("show/{id}")
    public ResponseEntity<Payment> findByHoaDon(@PathVariable("id") Integer id)
    {
        return ResponseEntity.ok(ordersService.findByPaymentById(id).get());
    }
}