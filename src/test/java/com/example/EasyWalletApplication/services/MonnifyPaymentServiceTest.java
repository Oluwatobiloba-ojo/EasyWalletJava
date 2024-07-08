package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.config.BeanConfig;
import com.example.EasyWalletApplication.dto.request.InitializePayment;
import com.example.EasyWalletApplication.dto.request.MonnifyInitializePayment;
import com.example.EasyWalletApplication.dto.response.InitializePaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MonnifyPaymentServiceTest {
    @Autowired
    @Qualifier("monnifyPayment")
    private PaymentService paymentService;
    @Autowired
    private BeanConfig beanConfig;


    @Test
    public void testInitiateMonnifyPaymentService(){
        InitializePayment<MonnifyInitializePayment> request = new InitializePayment<>();
        MonnifyInitializePayment monnifyInitializePayment = new MonnifyInitializePayment();
        monnifyInitializePayment.setPaymentDescription("Shopping mall");
        monnifyInitializePayment.setAmount(BigDecimal.valueOf(100));
        monnifyInitializePayment.setCustomerName("Oluwatobi");
        monnifyInitializePayment.setCustomerEmail("ojot630@gmail.com");
        monnifyInitializePayment.setCurrencyCode("NGN");
        monnifyInitializePayment.setContractCode(beanConfig.getMonnifyContractCode());
        monnifyInitializePayment.setPaymentReference(UUID.randomUUID().toString());
        request.setData(monnifyInitializePayment);
        InitializePaymentResponse response = paymentService.initializeTransaction(request);
        assertNotNull(response);
        assertThat(response.isStatus()).isTrue();
        assertThat(response.getReference()).isNotNull();
        assertThat(response.getUrl()).isNotNull();
    }

}
