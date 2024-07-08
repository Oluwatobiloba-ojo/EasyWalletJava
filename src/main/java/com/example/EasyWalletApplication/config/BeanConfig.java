package com.example.EasyWalletApplication.config;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class BeanConfig{
    @Value("${PAYSTACK_SECRET_KEY}")
    public String payStackSecretKey;
    @Value("${PAYSTACK_INITIALIZE_TRANSACTION_URL}")
    private String payStackUrl;
    @Value("${MONIFY_CONTRACT_CODE}")
    private String monnifyContractCode;
    @Value("${MONNIFY_SECRET_KEY}")
    private String monnifySecretKey;
    @Value("${MONNIFY_API_KEY}")
    private String monnifyApiKey;
    @Value("${MONNIFY_INIT_URL}")
    private String monnifyUrl;


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
