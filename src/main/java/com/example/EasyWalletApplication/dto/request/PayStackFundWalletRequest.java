package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayStackFundWalletRequest {
    private PayStackData data;
    private String event;
}
