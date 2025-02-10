package com.crm.customer_service.service;

import com.crm.customer_service.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(Long id, CustomerDto customerDto);
    CustomerDto getCustomerById(Long id);
    List<CustomerDto> getAllCustomers(String filter, String sortBy, String sortOrder, int page, int size);
    void deleteCustomer(Long id);
}
