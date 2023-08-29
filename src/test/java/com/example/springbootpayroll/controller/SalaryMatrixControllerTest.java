package com.example.springbootpayroll.controller;

import com.example.springbootpayroll.exception.IsExistsException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.SalaryMatrixRequest;
import com.example.springbootpayroll.model.dto.response.ResponseData;
import com.example.springbootpayroll.model.dto.response.ResponseError;
import com.example.springbootpayroll.model.entity.SalaryMatrix;
import com.example.springbootpayroll.service.SalaryMatrixService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SalaryMatrixController.class)
@AutoConfigureMockMvc
public class SalaryMatrixControllerTest {
    @MockBean
    private SalaryMatrixService salaryMatrixService;

    @Autowired
    private SalaryMatrixController salaryMatrixController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddSalaryMatrix_Success() throws Exception {
        SalaryMatrixRequest request = new SalaryMatrixRequest(2, 5000.0F, 1000.0F, 10.0F, 300.0F);

        SalaryMatrix salaryMatrix = new SalaryMatrix();
        salaryMatrix.setId("id");

        ResponseData responseData = new ResponseData(HttpStatus.CREATED.value(), "Success", salaryMatrix);

        when(salaryMatrixService.add(request)).thenReturn(salaryMatrix);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/matrix")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data.id").value(salaryMatrix.getId()));

        verify(salaryMatrixService, times(1)).add(request);

    }

    @Test
    public void testAddSalaryMatrix_GradeIsExists() throws Exception {
        SalaryMatrixRequest request = new SalaryMatrixRequest(2, 5000.0F, 1000.0F, 10.0F, 300.0F);

        SalaryMatrix salaryMatrix = new SalaryMatrix();
        salaryMatrix.setId("id");

        SalaryMatrix salaryMatrix1 = new SalaryMatrix();
        salaryMatrix1.setGrade(2);

        ResponseError responseError = new ResponseError(HttpStatus.CONFLICT.value(), "Is Exists","grade is exists");

        when(salaryMatrixService.add(request)).thenThrow(new IsExistsException("grade is exists"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/matrix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(responseError.getCode()))
                .andExpect(jsonPath("$.message").value(responseError.getMessage()))
                .andExpect(jsonPath("$.error").value(responseError.getError()));

        verify(salaryMatrixService, times(1)).add(request);

    }

    @Test
    public void testAddSalaryMatrix_MinusValue() throws Exception {
        SalaryMatrixRequest request = new SalaryMatrixRequest(2, -5000.0F, 1000.0F, 10.0F, 300.0F);

        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), "Bad Request","{salary=salary must be greater than 0}");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/matrix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(responseError.getCode()))
                .andExpect(jsonPath("$.message").value(responseError.getMessage()))
                .andExpect(jsonPath("$.error").value(responseError.getError()));

    }

    @Test
    public void testGetAllSalariesMatrix_Success() throws Exception {
        List<SalaryMatrix> salaryMatrixList = new ArrayList<>();
        salaryMatrixList.add(new SalaryMatrix());
        salaryMatrixList.add(new SalaryMatrix());

        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", salaryMatrixList);


        when(salaryMatrixService.getSalariesMatrix()).thenReturn(salaryMatrixList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data").isArray());

        verify(salaryMatrixService, times(1)).getSalariesMatrix();
    }

    @Test
    public void testGetSalaryMatrixById_Success() throws Exception {
        SalaryMatrix salaryMatrix = new SalaryMatrix();
        String id = "id";
        salaryMatrix.setId(id);

        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", salaryMatrix);

        when(salaryMatrixService.getSalaryMatrixById(id)).thenReturn(salaryMatrix);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/matrix/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data.id").value(salaryMatrix.getId()));

        verify(salaryMatrixService, times(1)).getSalaryMatrixById(id);
    }

    @Test
    public void testGetSalaryMatrixById_Failed() throws Exception {
        SalaryMatrix salaryMatrix = new SalaryMatrix();
        salaryMatrix.setId("id");

        ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND.value(), "Not Found","id is not exists");


        when(salaryMatrixService.getSalaryMatrixById("1")).thenThrow(new NotFoundException("id is not exists"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/matrix/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(responseError.getCode()))
                .andExpect(jsonPath("$.message").value(responseError.getMessage()))
                .andExpect(jsonPath("$.error").value(responseError.getError()));

        verify(salaryMatrixService, times(1)).getSalaryMatrixById("1");
    }

    @Test
    public void testUpdateSalaryMatrix_Success() throws Exception {
        SalaryMatrixRequest request = new SalaryMatrixRequest(2, 5000.0F, 1000.0F, 10.0F, 300.0F);

        SalaryMatrix salaryMatrix = new SalaryMatrix();
        salaryMatrix.setId("id");

        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", salaryMatrix);

        when(salaryMatrixService.updateSalaryMatrix("id", request)).thenReturn(salaryMatrix);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/matrix/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(responseData.getCode()))
                .andExpect(jsonPath("$.message").value(responseData.getMessage()))
                .andExpect(jsonPath("$.data.id").value(salaryMatrix.getId()));

        verify(salaryMatrixService, times(1)).updateSalaryMatrix("id", request);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
