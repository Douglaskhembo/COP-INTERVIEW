package com.mpesa.paymentsapp.repository;

import com.mpesa.paymentsapp.modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdNo(String idNo);

    @Query(value = """
    select p.cus_id , email,name, phone, p2.account_number, p.id_no\s
    from pms_customer p\s
    left join pms_accounts p2 on p.cus_id = p2.customer""", nativeQuery = true)
    List<Object[]> findAllCustomers();

    @Query(value = """
    select p.cus_id , email,name, phone, p2.account_number, p.id_no\s
    from pms_customer p\s
    left join pms_accounts p2 on p.cus_id = p2.customer
    WHERE cus_id = :cusId""", nativeQuery = true)
    List<Object[]> findByCustomerId(@Param("cusId") Long cusId);
}
