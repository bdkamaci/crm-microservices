package com.crm.auth_service.service.impl;

import com.crm.auth_service.client.UserClient;
import com.crm.auth_service.dto.AuthRequestDto;
import com.crm.auth_service.dto.AuthResponseDto;
import com.crm.auth_service.model.User;
import com.crm.auth_service.security.JwtUtil;
import com.crm.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserClient userClient;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequest) {
        User user = userClient.getUserByEmail(authRequest.getEmail());

        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDto(token);
    }
}
