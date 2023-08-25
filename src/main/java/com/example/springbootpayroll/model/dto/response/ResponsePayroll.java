package com.example.springbootpayroll.model.dto.response;

import com.example.springbootpayroll.model.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponsePayroll {

    private String id;

    private String employee;

    private String period;

    private Integer presence;

    private Integer notPresent;

    private Float salary;

    private Float paycut;

    private Float additionalSalary;

    private Float headOfFamily;

    private Float total;
}
