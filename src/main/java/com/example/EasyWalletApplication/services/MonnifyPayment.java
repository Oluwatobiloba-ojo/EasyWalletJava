package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.config.BeanConfig;
import com.example.EasyWalletApplication.dto.request.InitializePayment;
import com.example.EasyWalletApplication.dto.response.InitializePaymentResponse;
import com.example.EasyWalletApplication.dto.response.MonnifyResponse;
import com.example.EasyWalletApplication.util.External;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
@Service
public class MonnifyPayment implements PaymentService {
    @Autowired
    private BeanConfig beanConfig;

    @Override
    public InitializePaymentResponse initializeTransaction(InitializePayment<?> request) {
        InitializePaymentResponse initializePaymentResponse = new InitializePaymentResponse();
        String secret = beanConfig.getMonnifyApiKey()+":"+beanConfig.getMonnifySecretKey();
        String key = "Basic "+ Base64.getEncoder().encodeToString(secret.getBytes());
        ResponseEntity<MonnifyResponse> response = External.makeCall(key, request.getData(), beanConfig.getMonnifyUrl(), MonnifyResponse.class);
        System.out.println(response);
        System.out.println(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null){
            initializePaymentResponse.setStatus(false);
        }else {
            MonnifyResponse monnifyResponse = response.getBody();
            initializePaymentResponse.setUrl(monnifyResponse.getResponseBody().getCheckoutUrl());
            initializePaymentResponse.setReference(monnifyResponse.getResponseBody().getPaymentReference());
            initializePaymentResponse.setStatus(true);
        }
        return initializePaymentResponse;
    }
}
