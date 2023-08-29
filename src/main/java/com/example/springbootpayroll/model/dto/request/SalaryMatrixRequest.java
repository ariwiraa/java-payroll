package com.example.springbootpayroll.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryMatrixRequest {
    @NotNull(message = "grade is required")
    @Min(value = 1, message = "grade must be equal or greater than 1")
    private Integer grade;

    @NotNull(message = "salary is required")
    @Min(value = 0, message = "salary must be greater than 0")
    private Float salary;

    @NotNull(message = "paycut is required")
    @Min(value = 0, message = "paycut must be greater than 0")
    private Float paycut;

    @NotNull(message = "allowance is required")
    @Min(value = 0, message = "allowance must be greater than 0")
    private Float allowance;

    @NotNull(message = "head of family is required")
    @Min(value = 0, message = "head of family must be greater than 0")
    private Float headOfFamily;

}
