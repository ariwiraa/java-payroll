package com.example.springbootpayroll.service.impl;

import com.example.springbootpayroll.exception.IsExistsException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.PayrollRequest;
import com.example.springbootpayroll.model.dto.response.ResponsePayroll;
import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.model.entity.Payroll;
import com.example.springbootpayroll.model.entity.SalaryMatrix;
import com.example.springbootpayroll.repository.EmployeeRepository;
import com.example.springbootpayroll.repository.PayrollRepository;
import com.example.springbootpayroll.repository.SalaryMatrixRepository;
import com.example.springbootpayroll.service.EmployeeService;
import com.example.springbootpayroll.service.PayrollService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {
    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryMatrixRepository salaryMatrixRepository;


    @Override
    public ResponsePayroll add(PayrollRequest request) throws Exception {
        ResponsePayroll responsePayroll = new ResponsePayroll();
        boolean periodExists = payrollRepository.existsByPeriod(request.getPeriod());

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        if (!periodExists) {
            SalaryMatrix salaryMatrix = salaryMatrixRepository.findByGrade(employee.getGrade());

            Payroll payroll = new Payroll();

            payroll.setEmployee(employee);
            payroll.setPresence(request.getPresence());
            payroll.setNotPresent(request.getNotPresent());
            payroll.setPeriod(request.getPeriod());
            payroll.setSalary(salaryMatrix.getSalary());
            payroll.setPaycut(request.getNotPresent() * salaryMatrix.getPaycut());
            payroll.setAdditionalSalary(request.getPresence()*salaryMatrix.getAllowance());

            payroll = payrollRepository.save(payroll);

            if ("male".equals(employee.getGender()) && employee.isMarried()) {
                responsePayroll.setHeadOfFamily(salaryMatrix.getHeadOfFamily());
            }

            responsePayroll.setId(payroll.getId());
            responsePayroll.setEmployee(payroll.getEmployee().getId());
            responsePayroll.setPaycut(payroll.getPaycut());
            responsePayroll.setAdditionalSalary(payroll.getAdditionalSalary());
            responsePayroll.setSalary(payroll.getSalary());
            responsePayroll.setPeriod(payroll.getPeriod());
            responsePayroll.setPresence(payroll.getPresence());
            responsePayroll.setNotPresent(payroll.getNotPresent());

            Float headOfFamily = responsePayroll.getHeadOfFamily() != null ? responsePayroll.getHeadOfFamily() : 0;
            responsePayroll.setHeadOfFamily(headOfFamily);

            responsePayroll.setTotal(responsePayroll.getHeadOfFamily() + payroll.getAdditionalSalary() + payroll.getSalary() - payroll.getPaycut());

            return responsePayroll;
        }

        throw new IsExistsException("Period is exists");
    }

    @Override
    public Payroll getByEmployeeId(String employeeId) throws Exception {
       boolean exists = employeeRepository.existsById(employeeId);

       if (exists) {
            return payrollRepository.findByEmployeeId(employeeId);
       }

       throw new NotFoundException("employee not found");
    }
}