package com.example.EasyWalletApplication.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaystackResponse {
    private boolean status;
    private String message;
    private Data data;
}
