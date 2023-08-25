package com.example.springbootpayroll.model.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayrollRequest {
    private String employeeId;
    private String period;
    private Integer presence;
    private Integer notPresent;

}
