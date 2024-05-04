package com.example.clear_solutions_test_assignment.dto;

import com.example.clear_solutions_test_assignment.dto.markers.OnCreate;
import com.example.clear_solutions_test_assignment.dto.markers.OnFullUpdate;
import com.example.clear_solutions_test_assignment.dto.markers.OnSingleUpdate;
import com.example.clear_solutions_test_assignment.util.annotations.BirthDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDto(

        @NotNull(groups = {OnFullUpdate.class, OnSingleUpdate.class})
        Long id,

        @NotBlank(groups = {OnCreate.class, OnFullUpdate.class})
        @Email(groups = {OnCreate.class, OnFullUpdate.class})
        String email,

        @NotBlank(groups = {OnCreate.class, OnFullUpdate.class})
        String firstName,

        @NotBlank(groups = {OnCreate.class, OnFullUpdate.class})
        String lastName,

        @NotNull(groups = {OnCreate.class, OnFullUpdate.class})
        @BirthDate(groups = {OnCreate.class, OnFullUpdate.class})
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birthDate,

        @NotBlank(groups = OnSingleUpdate.class)
        @Pattern(regexp = "^\\d{10}$", groups = {OnCreate.class, OnFullUpdate.class, OnSingleUpdate.class})
        String phoneNumber,

        String address
) {
}

