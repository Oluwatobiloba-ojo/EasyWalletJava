package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InitializePayment<T> {
   private T data;
}
