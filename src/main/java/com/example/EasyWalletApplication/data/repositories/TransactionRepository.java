package com.example.EasyWalletApplication.data.repositories;

import com.example.EasyWalletApplication.data.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
