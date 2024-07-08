package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.dto.request.InitializePayment;
import com.example.EasyWalletApplication.dto.response.InitializePaymentResponse;

public interface PaymentService {
    InitializePaymentResponse initializeTransaction(InitializePayment<?> request);

}
