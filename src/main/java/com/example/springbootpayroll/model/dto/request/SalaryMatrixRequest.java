package com.example.springbootpayroll.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryMatrixRequest {
    @NotNull(message = "grade is required")
    @Max(value = 5, message = "grade is no more than 5")
    private Integer grade;

    @NotNull(message = "salary is required")
    private Float salary;

    @NotNull(message = "paycut is required")
    private Float paycut;

    @NotNull(message = "allowance is required")
    private Float allowance;

    @NotNull(message = "head of family is required")
    private Float headOfFamily;

}
