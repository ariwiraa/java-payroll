package com.example.springbootpayroll.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salaries_matrix")
public class SalaryMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private Integer grade;

    @Column(nullable = false)
    private Float salary;

    @Column(name = "pay_cut", nullable = false)
    private Float paycut;

    @Column(name = "allowance", nullable = false)
    private Float allowance;

    @Column(name = "head_of_family", nullable = false, columnDefinition = "FLOAT default 0.0")
    private Float headOfFamily;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }



}
