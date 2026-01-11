package io.zipcoder.persistenceapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_number")
    private Long departmentNumber;
    
    @NotEmpty
    @Column(name = "department_name")
    private String departmentName;
    
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee departmentManager;
    
    // Constructors
    public Department() {}
    
    // Getters and Setters
    public Long getDepartmentNumber() {
        return departmentNumber;
    }
    
    public void setDepartmentNumber(Long departmentNumber) {
        this.departmentNumber = departmentNumber;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public Employee getDepartmentManager() {
        return departmentManager;
    }
    
    public void setDepartmentManager(Employee departmentManager) {
        this.departmentManager = departmentManager;
    }
}
