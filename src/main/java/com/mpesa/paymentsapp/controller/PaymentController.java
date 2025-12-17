package com.mpesa.paymentsapp.controller;

import com.mpesa.paymentsapp.dto.AccountBalanceDTO;
import com.mpesa.paymentsapp.dto.AccountDTO;
import com.mpesa.paymentsapp.dto.CustomerDTO;
import com.mpesa.paymentsapp.dto.TransferRequest;
import com.mpesa.paymentsapp.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create/customer")
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDTO customer) {
        paymentService.addCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getAll/Customer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomer() {
        return ResponseEntity.ok(paymentService.findAllCustomers());
    }

    @GetMapping("/getCustomer/{cusId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long cusId) {
        return ResponseEntity.ok(paymentService.findCustomer(cusId));
    }

    @PostMapping("/create/account")
    public ResponseEntity<Void> createAccount(@RequestBody AccountDTO account) {
        paymentService.addAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/fundAccount")
    public ResponseEntity<Void> fundNow(@RequestBody AccountDTO request){
        paymentService.fundAccount(request);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferNow(@Valid @RequestBody TransferRequest request) {
        paymentService.initiateTrans(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/balance/{accNo}")
    public ResponseEntity<AccountBalanceDTO> getBalance(
            @PathVariable String accNo
    ) {
        return ResponseEntity.ok(paymentService.getBalance(accNo));
    }
}

