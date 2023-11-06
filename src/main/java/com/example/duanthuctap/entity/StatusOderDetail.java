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
@Table(name = "status_oder_detail")
public class StatusOderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @OneToMany(mappedBy = "statusOderDetail", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetailList;
}
