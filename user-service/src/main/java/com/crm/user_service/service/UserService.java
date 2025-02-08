package com.crm.user_service.service;

import com.crm.user_service.dto.UserDto;
import com.crm.user_service.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserUpdateDto updateDTO);
    void deleteUser(Long id);
}
