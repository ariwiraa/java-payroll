package com.example.springbootpayroll.service;

import com.example.springbootpayroll.model.dto.request.SalaryMatrixRequest;
import com.example.springbootpayroll.model.entity.SalaryMatrix;

import java.util.List;

public interface SalaryMatrixService {
    SalaryMatrix add(SalaryMatrixRequest request) throws Exception;

    List<SalaryMatrix> getSalariesMatrix();

    SalaryMatrix getSalaryMatrixById(String id) throws Exception;

    SalaryMatrix updateSalaryMatrix(String id, SalaryMatrixRequest request) throws Exception;
}
