package com.example.springbootpayroll.service.impl;

import com.example.springbootpayroll.exception.BadRequestException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.EmployeeRequest;
import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.model.entity.Gender;
import com.example.springbootpayroll.repository.EmployeeRepository;
import com.example.springbootpayroll.repository.SalaryMatrixRepository;
import com.example.springbootpayroll.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryMatrixRepository salaryMatrixRepository;

    @Override
    public Employee add(EmployeeRequest request) throws Exception {
        boolean exists = salaryMatrixRepository.existsByGrade(request.getGrade());

        if (exists) {
            Employee employee = new Employee();
            Gender gender;
            try {
                gender = Gender.valueOf(request.getGender().toUpperCase());
            } catch (Exception e) {
                throw new BadRequestException("Invalid gender value");
            }

            employee.setGender(gender);
            employee.setName(request.getName());
            employee.setGrade(request.getGrade());
            employee.setMarried(request.isMarried());
            return employeeRepository.save(employee);

        }

        throw new NotFoundException("grade is not exists");

    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(String id) throws Exception {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        }

        throw new NotFoundException("Employee is not exists");
    }
}
