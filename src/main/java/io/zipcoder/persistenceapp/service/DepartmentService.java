package io.zipcoder.persistenceapp.service;

import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.entity.Employee;
import io.zipcoder.persistenceapp.repository.DepartmentRepository;
import io.zipcoder.persistenceapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }
    
    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }
    
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    public Department setDepartmentManager(Long departmentId, Long managerId) {
        Optional<Department> dept = departmentRepository.findById(departmentId);
        
        if (dept.isPresent()) {
            Department department = dept.get();
            department.setManagerId(managerId);
            return departmentRepository.save(department);
        }
        return null;
    }
    
    public Department changeDepartmentName(Long departmentId, String newName) {
        Optional<Department> dept = departmentRepository.findById(departmentId);
        if (dept.isPresent()) {
            Department department = dept.get();
            department.setDepartmentName(newName);
            return departmentRepository.save(department);
        }
        return null;
    }
    
    public void mergeDepartments(String deptAName, String deptBName) {
        Optional<Department> deptA = departmentRepository.findByDepartmentName(deptAName);
        Optional<Department> deptB = departmentRepository.findByDepartmentName(deptBName);
        
        if (deptA.isPresent() && deptB.isPresent()) {
            Department departmentA = deptA.get();
            Department departmentB = deptB.get();
            
            if (departmentB.getManagerId() != null) {
                Optional<Employee> managerB = employeeRepository.findById(departmentB.getManagerId());
                if (managerB.isPresent() && departmentA.getManagerId() != null) {
                    Optional<Employee> managerA = employeeRepository.findById(departmentA.getManagerId());
                    if (managerA.isPresent()) {
                        Employee empB = managerB.get();
                        empB.setManager(managerA.get());
                        employeeRepository.save(empB);
                    }
                }
            }
            
            List<Employee> employeesInB = employeeRepository.findByDepartment_DepartmentNumber(departmentB.getDepartmentNumber());
            for (Employee emp : employeesInB) {
                emp.setDepartment(departmentA);
                employeeRepository.save(emp);
            }
            
            departmentRepository.delete(departmentB);
        }
    }
}