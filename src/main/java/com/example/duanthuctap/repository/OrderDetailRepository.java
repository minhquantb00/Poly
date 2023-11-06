package com.example.duanthuctap.repository;

import com.example.duanthuctap.dto.response.OrdersDetailResponse;
import com.example.duanthuctap.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Query("SELECT new com.example.duanthuctap.dto.response.OrdersDetailResponse" +
            "(o.codeOrder, o.totalPrice, o.updateAt, pm.paymentMethod, st.userName, p.nameProduct, od.quantity, p.price) " +
            "FROM OrderDetail od " +
            "JOIN od.orders o " +
            "JOIN od.product p " +
            "JOIN od.statusOderDetail sod " +
            "JOIN o.paymentList pm " +
            "JOIN o.staff st WHERE o.orderId = :id")
    List<OrdersDetailResponse> fillOrderDetail(@Param("id") Integer id);
}
