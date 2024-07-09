package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.data.models.Status;
import com.example.EasyWalletApplication.data.models.Transaction;
import com.example.EasyWalletApplication.dto.request.CreateTransactionRequest;
import com.example.EasyWalletApplication.dto.response.CreateTransactionResponse;
import com.example.EasyWalletApplication.exceptions.InvalidTransaction;

public interface TransactionService {
    CreateTransactionResponse createTransaction(CreateTransactionRequest request);

    Transaction findTransactionById(String referenceId) throws InvalidTransaction;

    Transaction updateTransaction(String referenceId, Status status) throws InvalidTransaction;
}