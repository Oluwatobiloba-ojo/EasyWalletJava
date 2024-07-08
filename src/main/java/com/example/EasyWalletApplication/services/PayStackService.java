package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.config.BeanConfig;
import com.example.EasyWalletApplication.dto.request.InitializePayment;
import com.example.EasyWalletApplication.dto.response.InitializePaymentResponse;
import com.example.EasyWalletApplication.dto.response.PaystackResponse;
import com.example.EasyWalletApplication.util.External;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PayStackService implements PaymentService{
    @Autowired
    private BeanConfig beanConfig;

    @Override
    public InitializePaymentResponse initializeTransaction(InitializePayment<?> request) {
        InitializePaymentResponse initializePaymentResponse = new InitializePaymentResponse();
        String key = "Bearer " + beanConfig.payStackSecretKey;
        ResponseEntity<PaystackResponse> response = External.makeCall(key, request.getData(), beanConfig.getPayStackUrl(), PaystackResponse.class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null){
            initializePaymentResponse.setStatus(false);
        }else {
            PaystackResponse paystackResponse = response.getBody();
            initializePaymentResponse.setStatus(paystackResponse.isStatus());
            initializePaymentResponse.setUrl(paystackResponse.getData().getAuthorization_url());
            initializePaymentResponse.setReference(paystackResponse.getData().getReference());
        }
        return initializePaymentResponse;
    }
}
