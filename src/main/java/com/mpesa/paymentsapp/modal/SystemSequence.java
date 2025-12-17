package com.mpesa.paymentsapp.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pms_sequence", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sequence_type", "sequence_date"})
})
public class SystemSequence{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sequenceType;
    private LocalDate sequenceDate;
    private Integer lastSequence;
}
