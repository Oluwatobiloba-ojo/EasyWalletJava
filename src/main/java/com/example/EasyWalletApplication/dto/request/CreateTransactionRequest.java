package com.example.EasyWalletApplication.dto.request;

import com.example.EasyWalletApplication.data.models.Account;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CreateTransactionRequest {
    private String description;
    private BigDecimal amount;
    private Account account;
    private String recipient_name;

    public CreateTransactionRequest(PerformTransactionRequest request, Account account) {
        this.account = account;
        this.description = request.getDescription();
        this.recipient_name = request.getRecipient_name();
        this.amount = request.getAmount();
    }
}
