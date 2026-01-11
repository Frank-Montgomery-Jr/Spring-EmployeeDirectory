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
        Optional<Employee> manager = employeeRepository.findById(managerId);
        
        if (dept.isPresent() && manager.isPresent()) {
            Department department = dept.get();
            department.setDepartmentManager(manager.get());
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
            
            Employee managerB = departmentB.getDepartmentManager();
            if (managerB != null) {
                managerB.setManager(departmentA.getDepartmentManager());
                employeeRepository.save(managerB);
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
