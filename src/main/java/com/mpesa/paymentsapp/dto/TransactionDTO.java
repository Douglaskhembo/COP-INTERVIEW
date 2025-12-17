package com.mpesa.paymentsapp.dto;

import com.mpesa.paymentsapp.modal.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long id;
    private BigDecimal transAmount;
    private String transType;
    private LocalDateTime transDate;
    private String transRefNo;
    private Customer customer;
}
