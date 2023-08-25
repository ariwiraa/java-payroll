package com.example.springbootpayroll.controller;

import com.example.springbootpayroll.model.dto.request.SalaryMatrixRequest;
import com.example.springbootpayroll.model.dto.response.ResponseData;
import com.example.springbootpayroll.model.entity.SalaryMatrix;
import com.example.springbootpayroll.service.SalaryMatrixService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/matrix")
public class SalaryMatrixController {
    @Autowired
    private SalaryMatrixService salaryMatrixService;

    private ResponseData<Object> responseData;

    @PostMapping
    public ResponseEntity<?> addSalaryMatrix(@Valid @RequestBody SalaryMatrixRequest request) throws Exception {
        SalaryMatrix salaryMatrix = salaryMatrixService.add(request);

        responseData = new ResponseData<>(201, "Success", salaryMatrix);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping
    public ResponseEntity<?> getAllSalariesMatrix() {
        List<SalaryMatrix> salaryMatrixList = salaryMatrixService.getSalariesMatrix();

        responseData = new ResponseData<>(200, "Success", salaryMatrixList);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaryMatrix(@PathVariable String id) throws Exception {
        SalaryMatrix salaryMatrix = salaryMatrixService.getSalaryMatrixById(id);

        responseData = new ResponseData<>(200, "Success", salaryMatrix);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalaryMatrix(@PathVariable String id, @Valid @RequestBody SalaryMatrixRequest request) throws Exception {
        SalaryMatrix salaryMatrix = salaryMatrixService.updateSalaryMatrix(id, request);

        responseData = new ResponseData<>(200, "Success", salaryMatrix);
        return ResponseEntity.status(responseData.getCode()).body(responseData);
    }


}
