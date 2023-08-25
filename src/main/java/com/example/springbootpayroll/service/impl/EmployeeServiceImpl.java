package com.example.springbootpayroll.service.impl;

import com.example.springbootpayroll.exception.BadRequestException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.EmployeeRequest;
import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.repository.EmployeeRepository;
import com.example.springbootpayroll.repository.SalaryMatrixRepository;
import com.example.springbootpayroll.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

            employee.setName(request.getName());
            employee.setGender(request.getGender());
            employee.setMarried(request.isMarried());
            employee.setGrade(request.getGrade());

            employee = employeeRepository.save(employee);

            return employee;
        }

        throw new BadRequestException("Grade is not exists");

    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(String id) throws Exception {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            return  optionalEmployee.get();
        }

        throw new NotFoundException("Employee is not exists");
    }
}
