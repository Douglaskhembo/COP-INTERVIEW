package com.mpesa.paymentsapp.repository;

import com.mpesa.paymentsapp.modal.Account;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String debitAcc);
}
