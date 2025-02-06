package com.crm.auth_service.service;

import com.crm.auth_service.dto.request.SignupRequest;
import com.crm.auth_service.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(SignupRequest signUpRequest);
    Optional<User> findByEmail(String email);
}
