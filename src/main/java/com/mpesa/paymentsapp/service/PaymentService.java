package com.mpesa.paymentsapp.service;

import com.mpesa.paymentsapp.dto.*;
import com.mpesa.paymentsapp.exceptions.BadRequestException;
import com.mpesa.paymentsapp.modal.Account;
import com.mpesa.paymentsapp.modal.Customer;
import com.mpesa.paymentsapp.modal.Transaction;
import com.mpesa.paymentsapp.repository.AccountRepo;
import com.mpesa.paymentsapp.repository.CustomerRepo;
import com.mpesa.paymentsapp.repository.TransactionRepo;
import com.mpesa.paymentsapp.utils.SequenceSetups;
import com.mpesa.paymentsapp.utils.TransactionType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final CustomerRepo customerRepo;
    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;
    private final TransactionProducer transactionProducer;
    private final SequenceSetups sequenceSetups;

    public Customer addCustomer(CustomerDTO customer) {

        if (customerRepo.findByIdNo(customer.getIdNo()).isPresent()) {
            throw new BadRequestException("Customer already exists");
        }

        Customer newCustomer = Customer.builder()
                .idNo(customer.getIdNo())
                .name(customer.getName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();

        return customerRepo.save(newCustomer);
    }

    public Account addAccount(AccountDTO account) {

        Customer customer = customerRepo.findById(account.getCusId())
                .orElseThrow(() -> new BadRequestException("Customer not found"));

        String accountNumber = sequenceSetups.generateReference();

        Account newAccount = Account.builder()
                .accountName(account.getAccountName())
                .accountNumber(accountNumber)
                .customer(customer)
                .accountType(AccountType.valueOf(account.getAccountType()))
                .balance(BigDecimal.ZERO)
                .build();

        return accountRepo.save(newAccount);
    }

    public Account fundAccount(AccountDTO request) {
        Account depoAcc = accountRepo.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new BadRequestException("Account not found"));

        depoAcc.setBalance(depoAcc.getBalance().add(request.getAmount()));
        return accountRepo.save(depoAcc);
    }

    public void initiateTrans(@Valid TransferRequest request) {

        Account debitAcc = accountRepo.findByAccountNumber(request.getDebitAcc())
                .orElseThrow(() -> new BadRequestException("Debit account not found"));

        Account creditAcc = accountRepo.findByAccountNumber(request.getCreditAcc())
                .orElseThrow(() -> new BadRequestException("Credit account not found"));

        if(debitAcc == creditAcc) {
            throw  new BadRequestException("Debit account and Credit account are the same");
        }

        if (debitAcc.getBalance().compareTo(request.getTransAmount()) < 0) {
            throw new BadRequestException("Insufficient funds");
        }

        Customer customer = customerRepo.findById(request.getCusId())
                .orElseThrow(() -> new BadRequestException("Customer not found"));

        TransactionType transType = TransactionType.valueOf(request.getTransType());

        Transaction transaction = Transaction.builder()
                .transType(transType)
                .transRefNo(UUID.randomUUID().toString())
                .transDate(LocalDateTime.now())
                .customer(customer)
                .transAmount(request.getTransAmount())
                .build();

        Transaction savedTrans = transactionRepo.save(transaction);

        debitAcc.setBalance(debitAcc.getBalance().subtract(request.getTransAmount()));
        creditAcc.setBalance(creditAcc.getBalance().add(request.getTransAmount()));

        accountRepo.save(debitAcc);
        accountRepo.save(creditAcc);

        transactionProducer.sendTransaction("Transaction with reference %s Completed Successfully" ,savedTrans.getTransRefNo());
    }

    public AccountBalanceDTO getBalance(String accNo) {

        Account acc = accountRepo.findByAccountNumber(accNo)
                .orElseThrow(() -> new BadRequestException("Account not found"));

        return new AccountBalanceDTO(
                acc.getAccountNumber(),
                acc.getBalance()
        );
    }

    public List<CustomerDTO> findAllCustomers() {
        List<Object[]> customer = customerRepo.findAllCustomers();
        if (CollectionUtils.isEmpty(customer)) {
            return List.of();
        }
        return customer.stream()
                .map(this:: mapToCustomerDTO)
                .toList();
    }

    public CustomerDTO findCustomer(Long cusId) {
        List<Object[]> customer = customerRepo.findByCustomerId(cusId);
        if (CollectionUtils.isEmpty(customer)) {
            throw new BadRequestException("Contributions not found");
        }
        return mapToCustomerDTO(customer.get(0));

    }

    private CustomerDTO mapToCustomerDTO(Object[] obj) {
        return CustomerDTO.builder()
                .cusId((Long) obj[0])
                .email((String) obj[1])
                .name((String) obj[2])
                .phone((String) obj[3])
                .accNo((String) obj[4])
                .idNo((String) obj[5])
                .build();
    }

}

