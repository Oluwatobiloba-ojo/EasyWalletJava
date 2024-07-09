package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.dto.request.CreateAccountRequest;
import com.example.EasyWalletApplication.dto.request.FundWalletRequest;
import com.example.EasyWalletApplication.dto.request.PerformTransactionRequest;
import com.example.EasyWalletApplication.dto.response.*;
import com.example.EasyWalletApplication.exceptions.AccountAlreadyExist;
import com.example.EasyWalletApplication.exceptions.AuthorizationException;
import com.example.EasyWalletApplication.exceptions.InvalidTransaction;

import java.util.List;

public interface WalletService {

    CreateWalletResponse createAccount(CreateAccountRequest request) throws AccountAlreadyExist;
    List<WalletAccountResponse> findAllAccounts();
    ProfileResponse getProfile(String accountNumber, String pin) throws AccountAlreadyExist, AuthorizationException;
    PerformTransactionResponse performTransaction(PerformTransactionRequest request) throws AccountAlreadyExist, InvalidTransaction;
    void fundWallet(FundWalletRequest request) throws InvalidTransaction;
    List<TransactionResponse> findAllTrasanctions(String accountNumber, String pin) throws AccountAlreadyExist, AuthorizationException;
}
