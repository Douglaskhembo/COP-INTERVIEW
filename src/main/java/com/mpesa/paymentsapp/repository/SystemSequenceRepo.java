package com.mpesa.paymentsapp.repository;

import com.mpesa.paymentsapp.modal.SystemSequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SystemSequenceRepo extends JpaRepository<SystemSequence, Long> {
    Optional<SystemSequence> findBySequenceDate(LocalDate today);
}
