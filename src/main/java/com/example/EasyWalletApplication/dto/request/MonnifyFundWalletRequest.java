package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonnifyFundWalletRequest {
    private String eventType;
    private MonnifyData eventData;
}
