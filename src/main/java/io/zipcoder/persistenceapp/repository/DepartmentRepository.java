package io.zipcoder.persistenceapp.repository;

import io.zipcoder.persistenceapp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    Optional<Department> findByDepartmentName(String departmentName);
}
