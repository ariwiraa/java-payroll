package com.example.springbootpayroll.service;

import com.example.springbootpayroll.exception.BadRequestException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.EmployeeRequest;
import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.model.entity.Gender;
import com.example.springbootpayroll.model.entity.SalaryMatrix;
import com.example.springbootpayroll.repository.EmployeeRepository;
import com.example.springbootpayroll.repository.SalaryMatrixRepository;
import com.example.springbootpayroll.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryMatrixRepository salaryMatrixRepository;

    @InjectMocks
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @BeforeEach
    public void init() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1", "ari", 2, Gender.MALE, false, new Date(), new Date()));
        employees.add(new Employee("2", "wira", 2, Gender.MALE, false, new Date(), new Date()));

        SalaryMatrix salaryMatrix = new SalaryMatrix();
        salaryMatrix.setGrade(2);

        lenient().when(salaryMatrixRepository.existsByGrade(2)).thenReturn(true);

        lenient().when(employeeRepository.findById("5")).thenReturn(Optional.empty());
        lenient().when(employeeRepository.findAll()).thenReturn(employees);
        lenient().when(employeeRepository.findById(anyString())).thenReturn(Optional.of(
                new Employee("1", "ari", 2, Gender.MALE, false, new Date(), new Date())
        ));
        lenient().when(employeeRepository.save(any())).thenReturn(
                new Employee("2", "wira", 2, Gender.MALE, false, new Date(), new Date())
        );
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertEquals(2, allEmployees.size());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = employeeService.getEmployeeById("1");
        assertNotNull(employee);
        assertEquals("1", employee.getId());
    }

    @Test
    public void testAddEmployee() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest("wira", "MALE", false, 2);
        Employee savedEmployee = employeeService.add(employeeRequest);
        assertNotNull(savedEmployee);
        assertEquals("wira", savedEmployee.getName()); // Since the mock returns the second employee
    }

    @Test
    public void testAddEmployee_thenInvalidGender() {
        EmployeeRequest employeeRequest = new EmployeeRequest("wira", "invalid", false, 2);

        assertThrows(BadRequestException.class, () -> {
            employeeService.add(employeeRequest);
        });
    }

    @Test
    public void testAddEmployee_thenGradeIsNotExists() {
        EmployeeRequest employeeRequest = new EmployeeRequest("wira", "MALE", false, 4);

        assertThrows(NotFoundException.class, () -> {
            employeeService.add(employeeRequest);
        });
    }

    @Test
    public void testGetEmployeeById_thenIdIsNotExists() {
        when(employeeRepository.findById("5")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            employeeService.getEmployeeById("5");
        });
    }

}