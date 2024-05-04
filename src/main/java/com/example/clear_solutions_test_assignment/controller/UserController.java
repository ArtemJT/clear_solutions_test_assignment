package com.example.clear_solutions_test_assignment.controller;

import com.example.clear_solutions_test_assignment.dto.UserDto;
import com.example.clear_solutions_test_assignment.dto.markers.OnCreate;
import com.example.clear_solutions_test_assignment.dto.markers.OnFullUpdate;
import com.example.clear_solutions_test_assignment.dto.markers.OnSingleUpdate;
import com.example.clear_solutions_test_assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@RequestBody @Validated(OnCreate.class) UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable(name = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/range")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllByBirthDateRange(
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate from,
            @RequestParam
            @DateTimeFormat(pattern = "dd/MM/yyyy")
            LocalDate to
    ) {
        return userService.getAllUsersByBirthDateRange(from, to);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserDto fullUpdateUser(@RequestBody @Validated(OnFullUpdate.class) UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PatchMapping("/update/phone")
    @ResponseStatus(HttpStatus.OK)
    public UserDto phoneUpdateUser(@RequestBody @Validated(OnSingleUpdate.class) UserDto userDto) {
        return userService.updateUserPhone(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable(name = "id") Long userId) {
        userService.deleteUserById(userId);
    }
}
