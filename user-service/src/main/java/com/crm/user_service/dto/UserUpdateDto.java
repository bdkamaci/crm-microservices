package com.crm.user_service.dto;

import com.crm.user_service.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String company;

    private Role role;

}
