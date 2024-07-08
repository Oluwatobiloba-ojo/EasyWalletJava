package com.example.EasyWalletApplication.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance = BigDecimal.ZERO;
    private String pin;
    @OneToOne
    private Profile profile;
}
