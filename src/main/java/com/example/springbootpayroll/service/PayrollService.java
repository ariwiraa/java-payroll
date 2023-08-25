package com.example.springbootpayroll.service;

import com.example.springbootpayroll.model.dto.request.PayrollRequest;
import com.example.springbootpayroll.model.dto.response.ResponsePayroll;
import com.example.springbootpayroll.model.entity.Payroll;

public interface PayrollService {
    ResponsePayroll add(PayrollRequest request) throws Exception;

    Payroll getByEmployeeId(String employeeId) throws Exception;
}
