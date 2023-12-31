package com.example.springbootpayroll.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollRequest {

    @NotBlank(message = "employee id is required")
    private String employeeId;

    @NotBlank(message = "month is required")
    private String month;

    @NotBlank(message = "year is required")
    private String year;

    @NotNull(message = "presence is required")
    @Min(value = 0, message = "presence must be equal or greater than 0")
    private Integer presence;

    @NotNull(message = "not present is required")
    @Min(value = 0, message = "not present must be equal or greater than 0")
    private Integer notPresent;

}
