package com.example.duanthuctap.service;

import com.example.duanthuctap.dto.request.OrderPaymentRequest;
import com.example.duanthuctap.dto.request.PaymentRequest;
import com.example.duanthuctap.dto.request.TransactionRequest;
import com.example.duanthuctap.dto.response.*;
import com.example.duanthuctap.entity.Payment;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface OrdersService {

    List<OrderResponse> showAllOrder(Integer pageNumber, Integer pageSize);

    List<OrdersDetailResponse> fillOrderDetail(Integer id);

    MessageResponse orderPayment(OrderPaymentRequest orderPaymentRequest);

    MessageResponse updateStatusOrder(String status, Integer idOrder, Integer idOrderDetail, String name);

    PaymentResponse callPaymentApi(HttpServletRequest req, TransactionRequest transactionRequest);

    TransactionResponse payTransaction(TransactionRequest transactionRequest);

    Optional<Payment> findByPaymentById(Integer id);
}
