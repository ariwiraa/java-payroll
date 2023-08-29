package com.example.springbootpayroll.model.dto.request;

import com.example.springbootpayroll.model.entity.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "gender is required")
    private String gender;

    private boolean married;

    @NotNull(message = "grade is required")
    @Min(value = 1, message = "presence must be equal or greater than 1")
    private Integer grade;
}
