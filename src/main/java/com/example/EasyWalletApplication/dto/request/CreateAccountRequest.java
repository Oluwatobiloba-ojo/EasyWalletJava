package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
    private String email;
    private String phoneNumber;
    private String pin;
    private String firstName;
    private String lastName;
}
