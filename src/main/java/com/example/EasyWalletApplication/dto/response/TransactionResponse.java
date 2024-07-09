package com.example.EasyWalletApplication.dto.response;

import com.example.EasyWalletApplication.data.models.Status;
import com.example.EasyWalletApplication.data.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {
    private BigDecimal amount;
    private String description;
    private String recipient_name;
    private Status status;
    private LocalDateTime createdAt;

    public TransactionResponse(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.createdAt = transaction.getCreatedAt();
        this.description = transaction.getDescription();
        this.recipient_name = transaction.getRecipient_name();
        this.status = transaction.getStatus();
    }
}
