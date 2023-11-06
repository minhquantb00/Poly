package com.example.duanthuctap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Integer orderStatusId;

    @Basic
    @Column(name = "status_name")
    private String statusName;

    @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY)
    private List<Orders> ordersList;

}
