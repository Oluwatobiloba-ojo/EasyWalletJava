package com.example.EasyWalletApplication.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateProfileRequest {
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}
