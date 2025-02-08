package com.crm.sales_service.client;

import com.crm.sales_service.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://customer-service")
public interface CustomerClient {
    @GetMapping("/api/customers/{customerId}")
    CustomerDto getCustomerById(@PathVariable("customerId") Long customerId);
}
