package com.example.clear_solutions_test_assignment.service;

import com.example.clear_solutions_test_assignment.dto.UserDto;
import com.example.clear_solutions_test_assignment.entity.User;
import com.example.clear_solutions_test_assignment.repository.UserRepository;
import com.example.clear_solutions_test_assignment.util.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.clear_solutions_test_assignment.context.TestContext.ID;
import static com.example.clear_solutions_test_assignment.context.TestContext.user;
import static com.example.clear_solutions_test_assignment.context.TestContext.userDto;
import static com.example.clear_solutions_test_assignment.context.TestContext.userDtoList;
import static com.example.clear_solutions_test_assignment.context.TestContext.userList;
import static com.example.clear_solutions_test_assignment.context.TestContext.userUpdatedDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Tests")
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Save user test")
    void saveTest() {
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto created = userService.saveUser(userDto);

        assertThat(created.firstName()).isSameAs(user.getFirstName());

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Get user by id test")
    void getByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(-1L)).thenThrow(EntityNotFoundException.class);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);
        UserDto found = userService.getUserById(1L);

        assertThat(found.firstName()).isSameAs(user.getFirstName());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(-1L));

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Get list of users by birth date range test")
    void getAllByBirthDateRangeTest() {
        LocalDate from = LocalDate.of(1998, 10, 1);
        LocalDate to = LocalDate.of(2002, 10, 1);

        when(userRepository.findAllByBirthDateBetween(from, to)).thenReturn(userList);
        when(userMapper.toDtos(anyList())).thenReturn(userDtoList);

        List<UserDto> found = userService.getAllUsersByBirthDateRange(from, to);
        assertThat(found).isNotEmpty();
        assertThat(found.getFirst().firstName()).isSameAs(user.getFirstName());

        verify(userRepository).findAllByBirthDateBetween(from, to);
    }

    @Test
    @DisplayName("Update user test")
    void updateFullTest() {
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto updated = userService.updateUser(userDto);

        assertThat(updated.firstName()).isSameAs(user.getFirstName());

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Update user phone test")
    void updatePhoneTest() {
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userUpdatedDto);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto updated = userService.updateUserPhone(userUpdatedDto);

        assertThat(updated.phoneNumber()).isSameAs(userUpdatedDto.phoneNumber());

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Delete user test")
    void deleteUserTest() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(userMapper.toEntity(any())).thenReturn(user);

        userService.deleteUserById(ID);

        verify(userRepository).delete(user);
    }
}
