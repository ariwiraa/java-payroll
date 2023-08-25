package com.example.springbootpayroll.repository;

import com.example.springbootpayroll.model.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, String> {
    Payroll findByEmployeeId(String employeeId);
    boolean existsByPeriod(String period);
}
