package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.dto.request.CreateAccountRequest;
import com.example.EasyWalletApplication.dto.request.FundWalletRequest;
import com.example.EasyWalletApplication.dto.request.PerformTransactionRequest;
import com.example.EasyWalletApplication.dto.response.CreateWalletResponse;
import com.example.EasyWalletApplication.dto.response.PerformTransactionResponse;
import com.example.EasyWalletApplication.dto.response.ProfileResponse;
import com.example.EasyWalletApplication.dto.response.WalletAccountResponse;
import com.example.EasyWalletApplication.exceptions.AccountAlreadyExist;
import com.example.EasyWalletApplication.exceptions.InvalidTransaction;

import java.util.List;

public interface WalletService {

    CreateWalletResponse createAccount(CreateAccountRequest request) throws AccountAlreadyExist;
    List<WalletAccountResponse> findAllAccounts();
    ProfileResponse getProfile(String accountNumber) throws AccountAlreadyExist;
    PerformTransactionResponse performTransaction(PerformTransactionRequest request) throws AccountAlreadyExist, InvalidTransaction;
    void fundWallet(FundWalletRequest request) throws InvalidTransaction;

}
