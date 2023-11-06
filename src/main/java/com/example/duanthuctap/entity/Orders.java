package com.example.duanthuctap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "code_oder")
    private String codeOrder;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "delivery_price")
    private Long deliveryPrice;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "date_of_payment")
    private LocalDate dateOfPayment;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @JsonBackReference
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    @JsonBackReference
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderDetail> orderDetailList;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Payment> paymentList;
}
