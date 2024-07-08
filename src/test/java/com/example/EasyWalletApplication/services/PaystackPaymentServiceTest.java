package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.dto.request.InitializePayment;
import com.example.EasyWalletApplication.dto.request.PaystackInitializePayment;
import com.example.EasyWalletApplication.dto.response.InitializePaymentResponse;
import com.example.EasyWalletApplication.exceptions.InitializePaymentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PaystackPaymentServiceTest {
    @Autowired
    @Qualifier("payStackService")
    private PaymentService paymentService;


    @Test
    public void testThatInitializePayment(){
        InitializePayment<PaystackInitializePayment> request = new InitializePayment<>();
        PaystackInitializePayment initializePayment = new PaystackInitializePayment();
        initializePayment.setAmount(BigDecimal.valueOf(10000));
        initializePayment.setReference(UUID.randomUUID().toString());
        initializePayment.setEmail("ojot630@gmail.com");
        request.setData(initializePayment);
        InitializePaymentResponse response = paymentService.initializeTransaction(request);
        assertThat(response).isNotNull();
        assertThat(response.isStatus()).isEqualTo(true);
        assertThat(response.getUrl()).isNotNull();
        assertThat(response.getReference()).isNotNull();
    }
}
