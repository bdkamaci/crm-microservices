package com.crm.sales_service.dto;

import com.crm.sales_service.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    private Long id;
    private Long customerId;
    private Status status;
    private LocalDateTime date;
    private LocalDateTime updatedAt;
}
