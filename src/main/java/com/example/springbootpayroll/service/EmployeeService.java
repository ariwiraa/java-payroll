package com.example.springbootpayroll.service;

import com.example.springbootpayroll.model.dto.request.EmployeeRequest;
import com.example.springbootpayroll.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee add(EmployeeRequest request) throws Exception;

    List<Employee> getAllEmployees();

    Employee getEmployeeById(String id) throws Exception;
}
