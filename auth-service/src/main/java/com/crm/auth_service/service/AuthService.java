package com.crm.auth_service.service;

import com.crm.auth_service.dto.AuthRequestDto;
import com.crm.auth_service.dto.AuthResponseDto;

public interface AuthService {
    AuthResponseDto login(AuthRequestDto authRequest);
}
