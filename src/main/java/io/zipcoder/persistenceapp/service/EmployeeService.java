package io.zipcoder.persistenceapp.service;


import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public List<Employee> getEmployeesByManager(Long managerId) {
        Optional<Employee> manager = employeeRepository.findById(managerId);
        if (manager.isPresent()) {
            return employeeRepository.findByManager(manager.get());
        }
        return new ArrayList<>();
    }
    
    public List<Employee> getEmployeesWithNoManager() {
        return employeeRepository.findByManagerIsNull();
    }
    
    public List<Employee> getEmployeesByDepartment(Long departmentNumber) {
        return employeeRepository.findByDepartment_DepartmentNumber(departmentNumber);
    }
    
    public List<Employee> getReportingHierarchy(Long employeeId) {
        List<Employee> hierarchy = new ArrayList<>();
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        
        if (employee.isPresent()) {
            Employee current = employee.get();
            while (current.getManager() != null) {
                hierarchy.add(current.getManager());
                current = current.getManager();
            }
        }
        return hierarchy;
    }
    
    public List<Employee> getAllReportsUnderManager(Long managerId) {
        return employeeRepository.findAllReportsUnderManager(managerId);
    }
    
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
    
    public void deleteEmployees(List<Long> employeeIds) {
        employeeIds.forEach(id -> employeeRepository.deleteById(id));
    }
    
    public void deleteEmployeesByDepartment(Long departmentNumber) {
        List<Employee> employees = getEmployeesByDepartment(departmentNumber);
        employeeRepository.deleteAll(employees);
    }
    
    public void deleteDirectReports(Long managerId) {
        Optional<Employee> manager = employeeRepository.findById(managerId);
        if (manager.isPresent()) {
            List<Employee> directReports = employeeRepository.findByManager(manager.get());
            Employee nextManager = manager.get().getManager();
            
            for (Employee report : directReports) {
                List<Employee> subReports = employeeRepository.findByManager(report);
                for (Employee subReport : subReports) {
                    subReport.setManager(nextManager);
                    employeeRepository.save(subReport);
                }
            }
            employeeRepository.deleteAll(directReports);
        }
    }
}
