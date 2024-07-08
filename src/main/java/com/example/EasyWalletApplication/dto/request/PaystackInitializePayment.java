package com.example.EasyWalletApplication.dto.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter
@Setter
@ToString
public class PaystackInitializePayment {
    private String email;
    private BigDecimal amount;
    private String reference;
}
