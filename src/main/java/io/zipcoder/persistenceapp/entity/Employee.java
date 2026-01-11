package io.zipcoder.persistenceapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_number")
    private Long employeeNumber;
    
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;
    
    @NotEmpty
    private String title;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Email
    @NotEmpty
    private String email;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    
    @ManyToOne
    @JoinColumn(name = "department_number")
    private Department department;
    
    // Constructors
    public Employee() {}
    
    // Getters and Setters
    public Long getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(Long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public void setManager(Employee manager) {
        this.manager = manager;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
}
