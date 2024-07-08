package com.example.EasyWalletApplication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletAccountResponse {
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
}
