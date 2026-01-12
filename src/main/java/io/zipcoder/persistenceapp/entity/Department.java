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
    
    @Column(name = "manager_id")
    private Long managerId;
    
    public Department() {}
    
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
    
    public Long getManagerId() {
        return managerId;
    }
    
    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
}
