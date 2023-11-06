package com.example.duanthuctap.repository;

import com.example.duanthuctap.dto.response.OrderResponse;
import com.example.duanthuctap.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT new com.example.duanthuctap.dto.response.OrderResponse" +
            "(od.codeOrder, us.userName, st.userName, od.createdAt, od.totalPrice, ods.statusName) " +
            "FROM Orders od " +
            "JOIN od.user us " +
            "LEFT JOIN od.staff st " +
            "JOIN od.paymentList pm " +
            "JOIN od.orderStatus ods")
    Page<OrderResponse> showAllOrder(Pageable pageable);

}
