package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundWalletRequest {
    private String status;
    private String reference;

    public FundWalletRequest(PayStackFundWalletRequest request){
        this.status = request.getEvent();
        this.reference = request.getData().getReference();
    }

    public FundWalletRequest(MonnifyFundWalletRequest request) {
        this.status = request.getEventType();
        this.reference = request.getEventData().getPaymentReference();
    }
}
