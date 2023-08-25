package com.example.springbootpayroll.repository;

import com.example.springbootpayroll.model.entity.SalaryMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryMatrixRepository extends JpaRepository<SalaryMatrix, String> {
    boolean existsByGrade(Integer grade);

    SalaryMatrix findByGrade(Integer grade);
}
