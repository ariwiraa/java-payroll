package com.example.springbootpayroll.service;

import com.example.springbootpayroll.exception.IsExistsException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.PayrollRequest;
import com.example.springbootpayroll.model.dto.response.ResponsePayroll;
import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.model.entity.Gender;
import com.example.springbootpayroll.model.entity.Payroll;
import com.example.springbootpayroll.model.entity.SalaryMatrix;
import com.example.springbootpayroll.repository.EmployeeRepository;
import com.example.springbootpayroll.repository.PayrollRepository;
import com.example.springbootpayroll.repository.SalaryMatrixRepository;
import com.example.springbootpayroll.service.impl.PayrollServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceTest {

    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private SalaryMatrixRepository salaryMatrixRepository;

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private PayrollService payrollService = new PayrollServiceImpl();

    @BeforeEach
    public void init() {
        SalaryMatrix salaryMatrix = new SalaryMatrix();
        salaryMatrix.setId("1");
        salaryMatrix.setGrade(1);
        salaryMatrix.setSalary(5000.0F);
        salaryMatrix.setAllowance(10.0F);
        salaryMatrix.setPaycut(1000.0F);
        salaryMatrix.setHeadOfFamily(300.0F);

        Employee employee = new Employee();
        employee.setId("1");
        employee.setGrade(1);
        employee.setGender(Gender.MALE);
        employee.setMarried(true);

        List<Payroll> payrolls = new ArrayList<>();

        payrolls.add(new Payroll("1", "June/2023", 20, 1, 5000.0F, 1000.0F, 10.0F, employee, new Date(), new Date()));
        payrolls.add(new Payroll("2", "August/2023", 20, 1, 5000.0F, 1000.0F, 10.0F, employee, new Date(), new Date()));

        lenient().when(salaryMatrixRepository.existsByGrade(anyInt())).thenReturn(true);
        lenient().when(salaryMatrixRepository.findByGrade(anyInt())).thenReturn(salaryMatrix);
        lenient().when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));

        lenient().when(payrollRepository.existsByPeriod("May/2023")).thenReturn(true);
        lenient().when(payrollRepository.findByEmployeeId(anyString())).thenReturn(
                new Payroll("1", "June/2023", 20, 1, 5000.0F, 1000.0F, 10.0F, employee, new Date(), new Date())
        );

        lenient().when(payrollRepository.save(any())).thenReturn(
                new Payroll("1", "June/2023", 20, 1, 5000.0F, 1000.0F, 10.0F, employee, new Date(), new Date())
        );
    }

    @Test
    public void testAddPayroll_thenCorrect() throws Exception {
        PayrollRequest payrollRequest = new PayrollRequest("1", "June", "2023", 20, 2);
        ResponsePayroll responsePayroll = payrollService.add(payrollRequest);

        assertEquals("June/2023", responsePayroll.getPeriod());
    }

    @Test
    public void testGetPayrollByEmployeeId_thenCorrect() throws Exception {
        ResponsePayroll responsePayroll = payrollService.getByEmployeeId("1");
        assertEquals("1", responsePayroll.getEmployee());
    }

    @Test
    public void testAddPayroll_thenPeriodExisting() {
        PayrollRequest payrollRequest = new PayrollRequest();
        payrollRequest.setMonth("May");
        payrollRequest.setYear("2023");
        payrollRequest.setEmployeeId("1");

        assertThrows(IsExistsException.class, () -> {
            payrollService.add(payrollRequest);
        });
    }
}
