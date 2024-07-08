package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class PerformTransactionRequest {
    private String account_number;
    private String description;
    private String payment_means;
    private BigDecimal amount;
    private String currency_change;
    private String recipient_name;
}
