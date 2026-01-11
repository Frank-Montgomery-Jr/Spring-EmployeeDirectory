package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/API/employees")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setEmployeeNumber(id);
        Employee updated = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updated);
    }
    
    @PutMapping("/{id}/manager/{managerId}")
    public ResponseEntity<Employee> setEmployeeManager(@PathVariable Long id, @PathVariable Long managerId) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        Optional<Employee> manager = employeeService.getEmployeeById(managerId);
        
        if (employee.isPresent() && manager.isPresent()) {
            Employee emp = employee.get();
            emp.setManager(manager.get());
            Employee updated = employeeService.updateEmployee(emp);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Employee>> getEmployeesByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getEmployeesByManager(managerId));
    }
    
    @GetMapping("/no-manager")
    public ResponseEntity<List<Employee>> getEmployeesWithNoManager() {
        return ResponseEntity.ok(employeeService.getEmployeesWithNoManager());
    }
    
    @GetMapping("/department/{departmentNumber}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable Long departmentNumber) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentNumber));
    }
    
    @GetMapping("/{id}/hierarchy")
    public ResponseEntity<List<Employee>> getReportingHierarchy(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getReportingHierarchy(id));
    }
    
    @GetMapping("/manager/{managerId}/all-reports")
    public ResponseEntity<List<Employee>> getAllReportsUnderManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getAllReportsUnderManager(managerId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteEmployees(@RequestBody List<Long> employeeIds) {
        employeeService.deleteEmployees(employeeIds);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/department/{departmentNumber}")
    public ResponseEntity<Void> deleteEmployeesByDepartment(@PathVariable Long departmentNumber) {
        employeeService.deleteEmployeesByDepartment(departmentNumber);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/manager/{managerId}/direct-reports")
    public ResponseEntity<Void> deleteDirectReports(@PathVariable Long managerId) {
        employeeService.deleteDirectReports(managerId);
        return ResponseEntity.noContent().build();
    }
}
