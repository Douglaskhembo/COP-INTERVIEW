package com.mpesa.paymentsapp.dto;

import java.math.BigDecimal;

public record AccountBalanceDTO(
        String accountNumber,
        BigDecimal balance
) {}
