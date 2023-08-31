package com.example.springbootpayroll.service;

import com.example.springbootpayroll.exception.IsExistsException;
import com.example.springbootpayroll.exception.NotFoundException;
import com.example.springbootpayroll.model.dto.request.SalaryMatrixRequest;
import com.example.springbootpayroll.model.entity.SalaryMatrix;
import com.example.springbootpayroll.repository.SalaryMatrixRepository;
import com.example.springbootpayroll.service.impl.SalaryMatrixServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalaryMatrixServiceTest {

    @Mock
    private SalaryMatrixRepository salaryMatrixRepository;

    @InjectMocks
    private SalaryMatrixService salaryMatrixService = new SalaryMatrixServiceImpl();

    @BeforeEach
    public void init() {
        List<SalaryMatrix> salaryMatrixList = new ArrayList<>();
        salaryMatrixList.add(new SalaryMatrix("1", 1, -5000.0F, 1000.0F, 10.0F, 300.0F, new Date(), new Date()));
        salaryMatrixList.add(new SalaryMatrix("2", 2, -5000.0F, 1000.0F, 10.0F, 300.0F, new Date(), new Date()));

//        lenient().when(salaryMatrixRepository.findById(anyString())).thenReturn(Optional.empty());
        lenient().when(salaryMatrixRepository.existsByGrade(5)).thenReturn(true);
        lenient().when(salaryMatrixRepository.findAll()).thenReturn(salaryMatrixList);
        lenient().when(salaryMatrixRepository.findById("2")).thenReturn(Optional.of(
                new SalaryMatrix("2", 2, 5000.0F, 1000.0F, 10.0F, 300.0F, new Date(), new Date())
        ));
        lenient().when(salaryMatrixRepository.save(any())).thenReturn(
                new SalaryMatrix("2", 2, 5000.0F, 1000.0F, 10.0F, 300.0F, new Date(), new Date())
        );

    }

    @Test
    public void testAddSalaryMatrix_thenCorrect() throws Exception {
        SalaryMatrixRequest salaryMatrixRequest = new SalaryMatrixRequest(2, 5000.0F, 1000.0F, 10.0F, 300.0F);
        SalaryMatrix salaryMatrix = salaryMatrixService.add(salaryMatrixRequest);
        assertNotNull(salaryMatrix);
        assertEquals(2, salaryMatrix.getGrade());
    }

    @Test
    public void testGetSalaryMatrixById_thenCorrect() throws Exception {
        SalaryMatrix salaryMatrix = salaryMatrixService.getSalaryMatrixById("2");
        assertNotNull(salaryMatrix);
        assertEquals("2", salaryMatrix.getId());
    }

    @Test
    public void testGetAllSalaryMatrices_thenCorrect() {
        List<SalaryMatrix> salaryMatrixList = salaryMatrixService.getSalariesMatrix();
        assertEquals(2, salaryMatrixList.size());
    }

    @Test
    public void testUpdateSalaryMatrix_thenCorrect() throws Exception {
        SalaryMatrixRequest salaryMatrixRequest = new SalaryMatrixRequest(2, -5000.0F, 1000.0F, 10.0F, 300.0F);
        SalaryMatrix updated = salaryMatrixService.updateSalaryMatrix("2", salaryMatrixRequest);
        assertEquals(2, updated.getGrade());
        assertEquals("2", updated.getId());

    }

    @Test
    public void testGetSalaryMatrixById_thenIdNotExists() throws Exception {
        when(salaryMatrixRepository.findById("5")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
           salaryMatrixService.getSalaryMatrixById("5");
        });

    }

    @Test()
    public void testAddSalaryMatrix_thenGradeExisting() {
        SalaryMatrixRequest request = new SalaryMatrixRequest();
        request.setGrade(5);

        assertThrows(IsExistsException.class, () -> {
            salaryMatrixService.add(request);
        });

    }

    @Test
    public void testUpdateSalaryMatrix_thenIdNotExist() throws Exception {
        SalaryMatrixRequest salaryMatrixRequest = new SalaryMatrixRequest(10, -5000.0F, 1000.0F, 10.0F, 300.0F);

        assertThrows(NotFoundException.class, () -> {
            salaryMatrixService.updateSalaryMatrix("10", salaryMatrixRequest);
        });

    }


}
