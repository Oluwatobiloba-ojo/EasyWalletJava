package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MonnifyInitializePayment {
    private BigDecimal amount;
    private String customerName;
    private String customerEmail;
    private String paymentReference;
    private String paymentDescription;
    private String  currencyCode;
    private String  contractCode;
}
