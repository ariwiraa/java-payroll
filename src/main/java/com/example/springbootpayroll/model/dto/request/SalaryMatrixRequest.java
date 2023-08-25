package com.example.springbootpayroll.model.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryMatrixRequest {
    private Integer grade;
    private Float salary;
    private Float paycut;
    private Float allowance;
    private Float headOfFamily;

}
