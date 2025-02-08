package com.crm.auth_service.model;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private Set<String> roles;
}
