package com.crm.sales_service.controller;

import com.crm.sales_service.dto.SaleDto;
import com.crm.sales_service.model.enums.Status;
import com.crm.sales_service.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleDto> createSale(@Valid @RequestBody SaleDto saleDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saleService.createSale(saleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDto> updateSale(
            @PathVariable Long id,
            @Valid @RequestBody SaleDto saleDto) {
        return ResponseEntity.ok(saleService.updateSale(id, saleDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDto> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping
    public ResponseEntity<List<SaleDto>> getAllSales(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Status status) {
        if (customerId != null && status != null) {
            return ResponseEntity.ok(saleService.getSalesByCustomerIdAndStatus(customerId, status));
        } else if (customerId != null) {
            return ResponseEntity.ok(saleService.getSalesByCustomerId(customerId));
        } else if (status != null) {
            return ResponseEntity.ok(saleService.getSalesByStatus(status));
        }
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
