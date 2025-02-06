package com.crm.sales_service.service;

import com.crm.sales_service.dto.SaleDto;
import com.crm.sales_service.model.enums.Status;

import java.util.List;

public interface SaleService {
    SaleDto createSale(SaleDto saleDto);
    SaleDto updateSale(Long id, SaleDto saleDto);
    SaleDto getSaleById(Long id);
    List<SaleDto> getAllSales();
    List<SaleDto> getSalesByCustomerId(Long customerId);
    List<SaleDto> getSalesByStatus(Status status);
    List<SaleDto> getSalesByCustomerIdAndStatus(Long customerId, Status status);
    void deleteSale(Long id);
}
