package com.example.EasyWalletApplication.controller;

import com.example.EasyWalletApplication.dto.request.*;
import com.example.EasyWalletApplication.dto.response.ApiResponse;
import com.example.EasyWalletApplication.exceptions.InvalidTransaction;
import com.example.EasyWalletApplication.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/")
public class AccountController {

    @Autowired
    private WalletService walletService;


    @PostMapping("user")
    public ResponseEntity<ApiResponse<?>> createAccount(@RequestBody CreateAccountRequest request){
        try{
            return ResponseEntity.ok(new ApiResponse<>(walletService.createAccount(request), true));
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }
    }

    @PostMapping("init_transaction")
    public ResponseEntity<ApiResponse<?>> initializeTransaction(@RequestBody PerformTransactionRequest request){
        try{
            return ResponseEntity.ok(new ApiResponse<>(walletService.performTransaction(request), true));
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }
    }

    @PostMapping("fund_wallet/paystack")
    public ResponseEntity<ApiResponse<?>> fundWallet(@RequestBody PayStackFundWalletRequest request){
        try{
            walletService.fundWallet(new FundWalletRequest(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidTransaction e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), false));
        }
    }

    @PostMapping("fund_wallet/monnify")
    public ResponseEntity<ApiResponse<?>> fundWallet(@RequestBody MonnifyFundWalletRequest request){
        try{
            walletService.fundWallet(new FundWalletRequest(request));
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (InvalidTransaction exception){
            return ResponseEntity.badRequest().body(new ApiResponse<>(exception.getMessage(), false));
        }

    }


}


