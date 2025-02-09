package com.crm.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteDto {
    private Long id;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
