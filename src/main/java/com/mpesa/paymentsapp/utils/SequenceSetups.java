package com.mpesa.paymentsapp.utils;

import com.mpesa.paymentsapp.modal.SystemSequence;
import com.mpesa.paymentsapp.repository.SystemSequenceRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class SequenceSetups {

    private final SystemSequenceRepo sequenceRepo;

    @Transactional
    public String generateReference() {
        LocalDate today = LocalDate.now();

        SystemSequence sequence = sequenceRepo.findBySequenceDate(today)
                .orElseGet(() -> sequenceRepo.save(SystemSequence.builder()
                        .sequenceDate(today)
                        .lastSequence(0)
                        .build()));

        sequence.setLastSequence(sequence.getLastSequence() + 1);
        sequenceRepo.save(sequence);

        return String.format("%s%03d",
                today.format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                sequence.getLastSequence());
    }
}
