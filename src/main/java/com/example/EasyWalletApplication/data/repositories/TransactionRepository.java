package com.example.EasyWalletApplication.data.repositories;

import com.example.EasyWalletApplication.data.models.Account;
import com.example.EasyWalletApplication.data.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByAccount(Account account);


}

