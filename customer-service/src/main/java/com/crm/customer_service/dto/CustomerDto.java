package com.crm.customer_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private List<NoteDto> notes;
}
