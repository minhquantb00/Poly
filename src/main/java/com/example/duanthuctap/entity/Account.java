package com.example.duanthuctap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "username")
    private String userName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "ResetPasswordToken")
    private String resetPasswordToken;

    @Column(name = "confirm_code")
    private String confirmCode;

    @Column(name = "ResetPasswordTokenExpiry")
    private LocalDate resetPasswordTokenExpiry;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "decentralization_id")
    @Enumerated(EnumType.STRING)
    private Decentralization decentralization;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<User> userList;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Staff> staffList;

}
