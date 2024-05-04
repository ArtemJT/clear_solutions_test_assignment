package com.example.clear_solutions_test_assignment.service;

import com.example.clear_solutions_test_assignment.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto user);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsersByBirthDateRange(LocalDate from, LocalDate to);

    UserDto updateUser(UserDto user);

    UserDto updateUserPhone(UserDto userDto);

    void deleteUserById(Long id);
}
