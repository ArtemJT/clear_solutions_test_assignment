package com.example.clear_solutions_test_assignment.service;

import com.example.clear_solutions_test_assignment.dto.UserDto;
import com.example.clear_solutions_test_assignment.entity.User;
import com.example.clear_solutions_test_assignment.repository.UserRepository;
import com.example.clear_solutions_test_assignment.util.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id=" + id + " not found"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) {
        UserDto userExistDto = getUserById(userDto.id());
        User userExist = userMapper.toEntity(userExistDto);
        userMapper.updateFromDto(userDto, userExist);
        return saveUser(userMapper.toDto(userExist));
    }

    @Override
    @Transactional
    public UserDto updateUserPhone(UserDto userDto) {
        User userExist = userMapper.toEntity(getUserById(userDto.id()));
        userExist.setPhoneNumber(userDto.phoneNumber());
        return saveUser(userMapper.toDto(userExist));
    }

    @Override
    public void deleteUserById(Long userId) {
        UserDto userById = getUserById(userId);
        userRepository.delete(userMapper.toEntity(userById));
    }

    @Override
    public List<UserDto> getAllUsersByBirthDateRange(LocalDate from, LocalDate to) {
        return userMapper.toDtos(userRepository.findAllByBirthDateBetween(from, to));
    }

}
