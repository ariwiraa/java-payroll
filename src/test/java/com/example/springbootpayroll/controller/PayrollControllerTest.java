package com.example.springbootpayroll.controller;

import com.example.springbootpayroll.model.dto.request.PayrollRequest;
import com.example.springbootpayroll.model.dto.response.ResponseData;
import com.example.springbootpayroll.model.dto.response.ResponsePayroll;
import com.example.springbootpayroll.service.PayrollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PayrollController.class)
@AutoConfigureMockMvc
public class PayrollControllerTest {

    @MockBean
    private PayrollService payrollService;

    @Autowired
    private PayrollController payrollController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddPayroll_Success() throws Exception {
        PayrollRequest request = new PayrollRequest("id","June", "2023", 20, 10);
        ResponsePayroll responsePayroll = new ResponsePayroll();

        ResponseData responseData = new ResponseData(HttpStatus.CREATED.value(), "Success", responsePayroll);

        when(payrollService.add(request)).thenReturn(responsePayroll);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/payroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data").exists());

        verify(payrollService, times(1)).add(request);
    }

    @Test
    public void testGetPayrollByEmployeeId_Success() throws Exception {
        ResponsePayroll responsePayroll = new ResponsePayroll();

        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", responsePayroll);

        when(payrollService.getByEmployeeId("employeeId")).thenReturn(responsePayroll);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/payroll/employeeId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data").exists());

        verify(payrollService, times(1)).getByEmployeeId("employeeId");
    }

    // Utility method to convert objects to JSON
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
