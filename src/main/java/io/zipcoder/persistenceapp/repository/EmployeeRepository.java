package io.zipcoder.persistenceapp.repository;

import io.zipcoder.persistenceapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByManager(Employee manager);
    
    List<Employee> findByManagerIsNull();
    
    List<Employee> findByDepartment_DepartmentNumber(Long departmentNumber);
    
    @Query("SELECT e FROM Employee e WHERE e.manager.employeeNumber = :managerId " +
           "OR e.manager.manager.employeeNumber = :managerId " +
           "OR e.manager.manager.manager.employeeNumber = :managerId")
    List<Employee> findAllReportsUnderManager(@Param("managerId") Long managerId);
}
