package com.crm.sales_service.service.impl;

import com.crm.sales_service.client.CustomerClient;
import com.crm.sales_service.dto.CustomerDto;
import com.crm.sales_service.dto.SaleDto;
import com.crm.sales_service.exception.custom.CustomerNotFoundException;
import com.crm.sales_service.model.Sale;
import com.crm.sales_service.model.enums.Status;
import com.crm.sales_service.repository.SaleRepository;
import com.crm.sales_service.service.SaleService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final CustomerClient customerClient;


    @Transactional
    @Override
    public SaleDto createSale(SaleDto saleDto) {
        // Customer Check
        try {
            CustomerDto customer = customerClient.getCustomerById(saleDto.getCustomerId());

            // Customer Verification
            if (customer == null) {
                throw new CustomerNotFoundException("Invalid customer ID: " + saleDto.getCustomerId());
            }

            Sale sale = modelMapper.map(saleDto, Sale.class);
            sale.setDate(LocalDateTime.now());
            sale.setUpdatedAt(LocalDateTime.now());
            sale.setStatus(Status.PENDING); // Default status

            Sale savedSale = saleRepository.save(sale);
            return modelMapper.map(savedSale, SaleDto.class);

        } catch (FeignException e) {
            throw new CustomerNotFoundException("Error in communicating with customer service: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public SaleDto updateSale(Long id, SaleDto saleDto) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        existingSale.setStatus(saleDto.getStatus());
        existingSale.setUpdatedAt(LocalDateTime.now());

        Sale updatedSale = saleRepository.save(existingSale);
        return modelMapper.map(updatedSale, SaleDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public SaleDto getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
        return modelMapper.map(sale, SaleDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SaleDto> getAllSales() {
        return saleRepository.findAll().stream()
                .map(sale -> modelMapper.map(sale, SaleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SaleDto> getSalesByCustomerId(Long customerId) {
        return saleRepository.findByCustomerId(customerId).stream()
                .map(sale -> modelMapper.map(sale, SaleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SaleDto> getSalesByStatus(Status status) {
        return saleRepository.findByStatus(status).stream()
                .map(sale -> modelMapper.map(sale, SaleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SaleDto> getSalesByCustomerIdAndStatus(Long customerId, Status status) {
        return saleRepository.findByCustomerIdAndStatus(customerId, status).stream()
                .map(sale -> modelMapper.map(sale, SaleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
        saleRepository.delete(sale);
    }
}
