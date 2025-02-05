package com.crm.sales_service.repository;

import com.crm.sales_service.model.Sale;
import com.crm.sales_service.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCustomerId(Long customerId);
    List<Sale> findByStatus(Status status);
    List<Sale> findByCustomerIdAndStatus(Long customerId, Status status);
}
