package com.example.EasyWalletApplication.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private BigDecimal amount;
    private String description;
    @ManyToOne
    private Account account;
    private String recipient_name;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PROCESSING;
}
