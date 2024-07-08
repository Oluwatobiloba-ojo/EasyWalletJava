package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.data.models.Status;
import com.example.EasyWalletApplication.data.models.Transaction;
import com.example.EasyWalletApplication.data.repositories.TransactionRepository;
import com.example.EasyWalletApplication.dto.request.CreateTransactionRequest;
import com.example.EasyWalletApplication.dto.response.CreateTransactionResponse;
import com.example.EasyWalletApplication.exceptions.InvalidTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.EasyWalletApplication.util.ApiUtil.TRANSACTION_DOES_NOT_EXIST;

@Service
public class EasyWalletTransactionService implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setAccount(request.getAccount());
        transaction.setRecipient_name(request.getRecipient_name());
        transaction.setDescription(request.getDescription());
        Transaction savedTransaction = transactionRepository.save(transaction);
        CreateTransactionResponse response = new CreateTransactionResponse();
        response.setId(savedTransaction.getId());
        return response;
    }

    @Override
    public Transaction findTransactionById(String referenceId) throws InvalidTransaction {
        return transactionRepository.findById(referenceId)
                .orElseThrow(() -> new InvalidTransaction(TRANSACTION_DOES_NOT_EXIST));
    }

    @Override
    public Transaction updateTransaction(String referenceId, Status status) throws InvalidTransaction {
        Transaction transaction = findTransactionById(referenceId);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }
}
