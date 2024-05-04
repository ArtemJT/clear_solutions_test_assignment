package com.example.clear_solutions_test_assignment.context;

import com.example.clear_solutions_test_assignment.dto.UserDto;
import com.example.clear_solutions_test_assignment.entity.User;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDate;
import java.util.List;

@TestComponent
public class TestContext {

    public static final Long ID = 1L;
    public static final String BASE_URL = "/api/v1/user";

    public static UserDto userDto = UserDto.builder()
            .id(ID)
            .firstName("Name")
            .lastName("LName")
            .email("email@mail.com")
            .birthDate(LocalDate.of(1999, 11, 11))
            .address("address")
            .phoneNumber("0123456789")
            .build();

    public static UserDto user2Dto = UserDto.builder()
            .id(2L)
            .firstName("Name2")
            .lastName("LName2")
            .email("email.2@mail.com")
            .birthDate(LocalDate.of(2002, 2, 2))
            .address("address2")
            .phoneNumber("9876543210")
            .build();

    public static UserDto userUpdatedDto = UserDto.builder()
            .id(ID)
            .firstName("UpdatedName")
            .lastName("UpdatedLName")
            .email("updated.email@mail.com")
            .birthDate(LocalDate.of(1991, 1, 1))
            .address("Updated.address")
            .phoneNumber("9876543210")
            .build();

    public static User user = User.builder()
            .id(ID)
            .firstName("Name")
            .lastName("LName")
            .email("email@mail.com")
            .birthDate(LocalDate.of(1999, 11, 11))
            .build();

    public static List<UserDto> userDtoList = List.of(userDto, user2Dto);
    public static List<User> userList = List.of(user);
}
