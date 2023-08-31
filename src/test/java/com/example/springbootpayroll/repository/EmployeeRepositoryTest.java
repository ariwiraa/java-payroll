package com.example.springbootpayroll.repository;

import com.example.springbootpayroll.model.entity.Employee;
import com.example.springbootpayroll.model.entity.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testCreateReadDelete() {
        Employee employee = new Employee("1", "ari", 2, Gender.MALE, false, new Date(), new Date());

        Employee savedEmployee = employeeRepository.save(employee);
        assertThat(savedEmployee).usingRecursiveComparison().ignoringFields("id", "createdAt", "updatedAt").isEqualTo(employee);

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).extracting(Employee::getName).containsOnlyOnce(employee.getName());

        employeeRepository.deleteAll();
        assertThat(employeeRepository.findAll()).isEmpty();

    }
}
