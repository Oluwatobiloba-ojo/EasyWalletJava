package com.example.EasyWalletApplication.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String paymentReference;
    private String checkoutUrl;
}
