package io.zipcoder.persistenceapp.controller;

import io.zipcoder.persistenceapp.entity.Department;
import io.zipcoder.persistenceapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/API/departments")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = departmentService.createDepartment(department);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        return department.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        department.setDepartmentNumber(id);
        Department updated = departmentService.updateDepartment(department);
        return ResponseEntity.ok(updated);
    }
    
    @PutMapping("/{id}/manager/{managerId}")
    public ResponseEntity<Department> setDepartmentManager(@PathVariable Long id, @PathVariable Long managerId) {
        Department updated = departmentService.setDepartmentManager(id, managerId);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/name")
    public ResponseEntity<Department> changeDepartmentName(@PathVariable Long id, @RequestBody String newName) {
        Department updated = departmentService.changeDepartmentName(id, newName);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/merge")
    public ResponseEntity<Void> mergeDepartments(@RequestParam String deptA, @RequestParam String deptB) {
        departmentService.mergeDepartments(deptA, deptB);
        return ResponseEntity.ok().build();
    }
}
