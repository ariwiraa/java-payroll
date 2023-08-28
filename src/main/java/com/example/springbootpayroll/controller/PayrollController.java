package com.example.springbootpayroll.controller;

import com.example.springbootpayroll.model.dto.request.PayrollRequest;
import com.example.springbootpayroll.model.dto.response.ResponseData;
import com.example.springbootpayroll.model.dto.response.ResponsePayroll;
import com.example.springbootpayroll.model.entity.Payroll;
import com.example.springbootpayroll.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payroll")
public class PayrollController {
    @Autowired
    private PayrollService payrollService;

    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<?> addPayroll(@Valid @RequestBody PayrollRequest request) throws Exception {
        ResponsePayroll responsePayroll = payrollService.add(request);

        responseData = new ResponseData(201, "Success", responsePayroll);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getPayrollByEmployeeId(@PathVariable String employeeId) throws Exception {
        ResponsePayroll responsePayroll = payrollService.getByEmployeeId(employeeId);

        responseData = new ResponseData(200, "Success", responsePayroll);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

}
