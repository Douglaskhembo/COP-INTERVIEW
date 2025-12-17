package com.mpesa.paymentsapp.dto;

import com.mpesa.paymentsapp.modal.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull
    @Positive
    private BigDecimal transAmount;
    @NotNull
    private String debitAcc;
    @NotNull
    private String creditAcc;

    @NotNull
    private Long cusId;

    @NotNull
    private String transType;
}
