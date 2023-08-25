package com.example.springbootpayroll.controller;

import com.example.springbootpayroll.model.dto.request.EmployeeRequest;
import com.example.springbootpayroll.model.dto.response.ResponseData;
import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    private ResponseData<Object> responseData;

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeRequest request) throws Exception {
        Employee employee = employeeService.add(request);

        responseData = new ResponseData<>(201, "Success", employee);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
       List<Employee> employees = employeeService.getAllEmployees();

        responseData = new ResponseData<>(200, "Success", employees);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) throws Exception {
        Employee employee = employeeService.getEmployeeById(id);

        responseData = new ResponseData<>(200, "Success", employee);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }
}
