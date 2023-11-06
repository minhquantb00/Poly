package com.example.duanthuctap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sole")
public class Sole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sole_id")
    private Integer soleId;

    @Column(name = "name_sole")
    private String nameSole;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
