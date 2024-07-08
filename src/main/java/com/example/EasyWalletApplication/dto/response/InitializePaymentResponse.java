package com.example.EasyWalletApplication.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InitializePaymentResponse {
    private String url;
    private String reference;
    private boolean status;
}
