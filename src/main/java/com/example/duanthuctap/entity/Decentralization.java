package com.example.duanthuctap.entity;

import com.example.duanthuctap.enums.VaiTroEnums;
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
@Table(name = "decentralization")
public class Decentralization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "decentralization_id")
    private Integer decentralizationId;

    @Column(name = "Authority_name")
    @Enumerated(EnumType.STRING)
    private VaiTroEnums authorityName;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "decentralization", fetch = FetchType.LAZY)
    private List<Account> accountList;

}
