package com.example.EasyWalletApplication.exceptions;

public class InvalidTransaction extends Exception {
    public InvalidTransaction(String message) {
        super(message);
    }
}
