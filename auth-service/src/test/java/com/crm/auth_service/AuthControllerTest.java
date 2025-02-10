package com.crm.auth_service;

import com.crm.auth_service.controller.AuthController;
import com.crm.auth_service.dto.AuthRequestDto;
import com.crm.auth_service.dto.AuthResponseDto;
import com.crm.auth_service.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void shouldReturnTokenWhenLoginIsSuccessful() throws Exception {
        // Given
        AuthRequestDto requestDto = new AuthRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password123");

        AuthResponseDto responseDto = new AuthResponseDto("mocked-jwt-token");

        when(authService.login(any(AuthRequestDto.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));

        verify(authService, times(1)).login(any(AuthRequestDto.class));
    }

    @Test
    void shouldReturnUnauthorizedWhenLoginFails() throws Exception {
        // Given
        when(authService.login(any(AuthRequestDto.class))).thenThrow(new RuntimeException("Invalid credentials"));

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isUnauthorized());

        verify(authService, times(1)).login(any(AuthRequestDto.class));
    }
}
