package com.mpesa.paymentsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Long cusId;
    private String name;
    private String email;
    private String phone;
    private String idNo;
    private String accNo;
}
