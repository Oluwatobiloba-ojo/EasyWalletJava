package com.example.EasyWalletApplication.dto.response;

import com.example.EasyWalletApplication.data.models.Account;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProfileResponse {
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private BigDecimal amount;

    public ProfileResponse(Account  account){
        this.email = account.getProfile().getEmail();
        this.firstName = account.getProfile().getFirstName();
        this.lastName = account.getProfile().getLastName();
        this.phoneNumber = account.getProfile().getPhoneNumber();
        this.amount = account.getAccountBalance();
    }
}
