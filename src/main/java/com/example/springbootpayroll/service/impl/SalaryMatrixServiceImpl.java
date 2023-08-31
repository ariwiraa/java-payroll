package com.example.springbootpayroll.service.impl;

import com.example.springbootpayroll.exception.IsExistsException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.SalaryMatrixRequest;
import com.example.springbootpayroll.model.entity.SalaryMatrix;
import com.example.springbootpayroll.repository.SalaryMatrixRepository;
import com.example.springbootpayroll.service.SalaryMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryMatrixServiceImpl implements SalaryMatrixService {
    @Autowired
    private SalaryMatrixRepository salaryMatrixRepository;

    @Override
    public SalaryMatrix add(SalaryMatrixRequest request) throws Exception {
        boolean exists = salaryMatrixRepository.existsByGrade(request.getGrade());

        if (!exists) {
            SalaryMatrix salaryMatrix = new SalaryMatrix();

            salaryMatrix.setSalary(request.getSalary());
            salaryMatrix.setAllowance(request.getAllowance());
            salaryMatrix.setGrade(request.getGrade());
            salaryMatrix.setPaycut(request.getPaycut());
            salaryMatrix.setHeadOfFamily(request.getHeadOfFamily());

            salaryMatrix = salaryMatrixRepository.save(salaryMatrix);

            return salaryMatrix;
        }

        throw new IsExistsException("Grade is exists");

    }

    @Override
    public List<SalaryMatrix> getSalariesMatrix() {
        return salaryMatrixRepository.findAll();
    }

    @Override
    public SalaryMatrix getSalaryMatrixById(String id) throws Exception {
        Optional<SalaryMatrix> optionalSalaryMatrix = salaryMatrixRepository.findById(id);

        if (optionalSalaryMatrix.isPresent()) {
            return optionalSalaryMatrix.get();
        }

        throw new NotFoundException("Salary Matrix not found");
    }


    @Override
    public SalaryMatrix updateSalaryMatrix(String id, SalaryMatrixRequest request) throws Exception {
        Optional<SalaryMatrix> optionalSalaryMatrix = salaryMatrixRepository.findById(id);

        if (optionalSalaryMatrix.isPresent()) {
            SalaryMatrix salaryMatrix = optionalSalaryMatrix.get();

            salaryMatrix.setId(id);
            salaryMatrix.setSalary(request.getSalary());
            salaryMatrix.setAllowance(request.getAllowance());
            salaryMatrix.setGrade(request.getGrade());
            salaryMatrix.setPaycut(request.getPaycut());
            salaryMatrix.setHeadOfFamily(request.getHeadOfFamily());

            salaryMatrix = salaryMatrixRepository.save(salaryMatrix);

            return salaryMatrix;

        }

        throw new NotFoundException("Id doesn't exists");
    }
}
