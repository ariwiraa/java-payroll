package com.example.springbootpayroll.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payrolls")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "period is required")
    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    @NotNull(message = "presence is required")
    private Integer presence;

    @NotNull(message = "presence is required")
    @Column(name = "not_present", nullable = false)
    private Integer notPresent;

    @Column(nullable = false)
    private Float salary;

    @Column(name = "pay_cut")
    private Float paycut;

    @Column(name = "additional_salary")
    private Float additionalSalary;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

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
