package com.crm.user_service.service.impl;

import com.crm.user_service.dto.UserDto;
import com.crm.user_service.dto.UserUpdateDto;
import com.crm.user_service.model.User;
import com.crm.user_service.repository.UserRepository;
import com.crm.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long id, UserUpdateDto updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updateDTO.getName());
        user.setPhone(updateDTO.getPhone());
        user.setCompany(updateDTO.getCompany());
        user.setRole(updateDTO.getRole());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.saveAndFlush(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
