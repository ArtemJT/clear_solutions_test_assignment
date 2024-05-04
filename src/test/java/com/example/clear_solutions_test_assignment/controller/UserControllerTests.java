package com.example.clear_solutions_test_assignment.controller;

import com.example.clear_solutions_test_assignment.dto.UserDto;
import com.example.clear_solutions_test_assignment.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.example.clear_solutions_test_assignment.context.TestContext.BASE_URL;
import static com.example.clear_solutions_test_assignment.context.TestContext.ID;
import static com.example.clear_solutions_test_assignment.context.TestContext.userDto;
import static com.example.clear_solutions_test_assignment.context.TestContext.userDtoList;
import static com.example.clear_solutions_test_assignment.context.TestContext.userUpdatedDto;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("User Controller Tests")
class UserControllerTests {

    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST " + BASE_URL)
    void saveUserTest() throws Exception {

        when(userService.saveUser(any(UserDto.class))).thenReturn(userDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(userDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value("Name"));

        verify(userService, times(1)).saveUser(any());
    }

    @Test
    @DisplayName("GET " + BASE_URL + "/{id}")
    void getUserByIdTest() throws Exception {
        String errorUrlTemplate = BASE_URL + "/0";

        //  When returns
        when(userService.getUserById(anyLong())).thenReturn(userDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get(BASE_URL + "/" + ID);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value("Name"));

        //  When throws
        when(userService.getUserById(anyLong())).thenThrow(EntityNotFoundException.class);

        MockHttpServletRequestBuilder mockErrorRequest = MockMvcRequestBuilders
                .get(errorUrlTemplate);

        mockMvc.perform(mockErrorRequest)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.path").value(errorUrlTemplate))
                .andExpect(jsonPath("$.errors").isMap())
                .andExpect(jsonPath("$.errors.EntityNotFoundException").exists());

        verify(userService, times(2)).getUserById(anyLong());
    }

    @Test
    @DisplayName("GET " + BASE_URL + "/range")
    void getAllByBirthDateRangeTest() throws Exception {
        String urlTemplate = BASE_URL + "/range";

        //  When returns
        when(userService.getAllUsersByBirthDateRange(isA(LocalDate.class), isA(LocalDate.class))).thenReturn(userDtoList);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get(urlTemplate)
                .param("from", "1999-11-11")
                .param("to", "2002-11-11");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*").isNotEmpty())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].firstName").value("Name"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Name2"));

        //  When throws
        MockHttpServletRequestBuilder mockErrorRequest = MockMvcRequestBuilders
                .get(urlTemplate)
                .param("from", "2002-11-11")
                .param("to", "2000-11-11");

        mockMvc.perform(mockErrorRequest)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.path").value(urlTemplate))
                .andExpect(jsonPath("$.errors").isMap())
                .andExpect(jsonPath("$.errors.DateRangeException").exists())
                .andExpect(jsonPath("$.errors.DateRangeException").value("Date 'FROM' must be earlier than 'TO'"));

        verify(userService, times(1))
                .getAllUsersByBirthDateRange(isA(LocalDate.class), isA(LocalDate.class));
    }

    @Test
    @DisplayName("PUT " + BASE_URL + "/update")
    void fullUpdateUser() throws Exception {
        when(userService.updateUser(any(UserDto.class))).thenReturn(userUpdatedDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(BASE_URL + "/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(userUpdatedDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value("UpdatedName"));

        verify(userService, times(1)).updateUser(any());
    }

    @Test
    @DisplayName("PATCH " + BASE_URL + "/update/phone")
    void phoneUpdateUserTest() throws Exception {
        UserDto updatePhoneDto = UserDto.builder().id(ID).phoneNumber("9876543210").build();

        when(userService.updateUserPhone(any(UserDto.class))).thenReturn(userUpdatedDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch(BASE_URL + "/update/phone")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(updatePhoneDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.firstName").value("UpdatedName"))
                .andExpect(jsonPath("$.phoneNumber").value("9876543210"));

        verify(userService, times(1)).updateUserPhone(any());
    }

    @Test
    @DisplayName("DELETE " + BASE_URL + "/{id}")
    void deleteUserById() throws Exception {
        doNothing().when(userService).deleteUserById(anyLong());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete(BASE_URL + "/" + ID);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUserById(anyLong());
    }
}
