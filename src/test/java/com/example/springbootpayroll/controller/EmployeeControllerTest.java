package com.example.springbootpayroll.controller;

import com.example.springbootpayroll.exception.BadRequestException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.EmployeeRequest;
import com.example.springbootpayroll.model.dto.response.ResponseData;
import com.example.springbootpayroll.model.dto.response.ResponseError;
import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenEmployeeControllerInjected_thenNotNull() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    @Test
    public void whenGetAllRequestToEmployee_thenCorrectResponse() throws Exception {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee());
        employees.add(new Employee());

        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", employees);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data").isArray());

        verify(employeeService, times(1)).getAllEmployees();

    }

    @Test
    public void whenGetByIdRequestToEmployee_thenCorrectResponse() throws Exception {
        UUID id = UUID.randomUUID();
        Employee employee = new Employee();

        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", employee);

        when(employeeService.getEmployeeById(id.toString())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data.id").value(employee.getId()));

        verify(employeeService, times(1)).getEmployeeById(id.toString());

    }
   

    @Test
    public void whenAddRequestToEmployee_thenCorrectResponse() throws Exception {

        EmployeeRequest request = new EmployeeRequest("Ari", "male", true, 2);
        Employee employee = new Employee();
        employee.setId("id");
        ResponseData responseData = new ResponseData(HttpStatus.CREATED.value(), "Success", employee);

        when(employeeService.add(request)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data.id").value(employee.getId()));

        verify(employeeService, times(1)).add(request);

    }

    @Test
    public void testAddEmployee_InvalidGender() throws Exception {
        EmployeeRequest request = new EmployeeRequest("Ari", "invalidGender", true, 2);

        when(employeeService.add(request)).thenThrow(new BadRequestException("Invalid gender value"));

        ResponseError responseError = new ResponseError(HttpStatus.CONFLICT.value(), "Invalid gender value");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(responseError.getCode()))
                .andExpect(jsonPath("$.error").value(responseError.getError()));

        verify(employeeService, times(1)).add(request);
    }

    @Test
    public void testAddEmployee_GradeNotFound() throws Exception {
        EmployeeRequest request = new EmployeeRequest("Ari", "male", true, 2);

        when(employeeService.add(request)).thenThrow(new NotFoundException("grade is not exists"));

        ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND.value(),  "grade is not exists");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(responseError.getCode()))
                .andExpect(jsonPath("$.error").value(responseError.getError()));

        verify(employeeService, times(1)).add(request);
    }

}
