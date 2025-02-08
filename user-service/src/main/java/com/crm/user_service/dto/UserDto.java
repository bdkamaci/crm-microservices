package com.crm.user_service.dto;

import com.crm.user_service.model.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String company;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
