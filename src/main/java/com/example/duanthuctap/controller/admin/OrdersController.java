package com.example.duanthuctap.controller.admin;

import com.example.duanthuctap.dto.request.OrderPaymentRequest;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.dto.response.OrderResponse;
import com.example.duanthuctap.dto.response.OrdersDetailResponse;
import com.example.duanthuctap.service.impl.OrdersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/")
public class OrdersController {

    @Autowired
    private OrdersServiceImpl ordersService;

    @GetMapping("show")
    public ResponseEntity<List<OrderResponse>> showOrder(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "9") Integer pageSize) {
        return new ResponseEntity<>(ordersService.showAllOrder(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("order-detail/{id}")
    public ResponseEntity<List<OrdersDetailResponse>> showOrder(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(ordersService.fillOrderDetail(id), HttpStatus.OK);
    }

    @PostMapping("update-status")
    public ResponseEntity<MessageResponse> updateStatusOrder(
            @RequestParam(name = "status") String status,
            @RequestParam(name = "idOrder") Integer idOrder,
            @RequestParam(name = "idOrderDetail") Integer idOrderDetail,
            Principal principal
    ) {
        return new ResponseEntity<>(ordersService.updateStatusOrder(status, idOrder, idOrderDetail, principal.getName()), HttpStatus.OK);
    }
}
