package com.mpesa.paymentsapp.modal;

import com.mpesa.paymentsapp.utils.TransactionType;
import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "pms_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal transAmount;
    @Enumerated(EnumType.STRING)
    private TransactionType transType;
    private LocalDateTime transDate;
    private String transRefNo;
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;
}
