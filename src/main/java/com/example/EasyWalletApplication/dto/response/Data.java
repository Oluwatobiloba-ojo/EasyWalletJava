package com.example.EasyWalletApplication.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Data {
    private String authorization_url;
    private String reference;
}
