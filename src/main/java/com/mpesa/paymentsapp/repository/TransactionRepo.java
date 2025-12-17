package com.mpesa.paymentsapp.repository;

import com.mpesa.paymentsapp.modal.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
