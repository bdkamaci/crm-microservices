package com.crm.auth_service;

import com.crm.auth_service.client.UserClient;
import com.crm.auth_service.dto.AuthRequestDto;
import com.crm.auth_service.dto.AuthResponseDto;
import com.crm.auth_service.model.User;
import com.crm.auth_service.security.JwtUtil;
import com.crm.auth_service.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserClient userClient;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private AuthRequestDto authRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequestDto();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password123");

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("$2a$10$abcde1234567890");
        mockUser.setRoles(Set.of("USER"));
    }

    @Test
    void shouldReturnTokenWhenCredentialsAreValid() {
        // Given
        when(userClient.getUserByEmail(authRequest.getEmail())).thenReturn(mockUser);
        when(passwordEncoder.matches(authRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(mockUser.getEmail())).thenReturn("mocked-jwt-token");

        // When
        AuthResponseDto response = authService.login(authRequest);

        // Then
        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userClient.getUserByEmail(authRequest.getEmail())).thenReturn(null);

        // Then
        Exception exception = assertThrows(RuntimeException.class, () -> authService.login(authRequest));
        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPasswordDoesNotMatch() {
        // Given
        when(userClient.getUserByEmail(authRequest.getEmail())).thenReturn(mockUser);
        when(passwordEncoder.matches(authRequest.getPassword(), mockUser.getPassword())).thenReturn(false);

        // Then
        Exception exception = assertThrows(RuntimeException.class, () -> authService.login(authRequest));
        assertEquals("Invalid credentials", exception.getMessage());
    }
}
