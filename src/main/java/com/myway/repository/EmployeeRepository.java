package com.myway.repository;

import com.myway.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ebru Göksal
 */
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmail(String email);
}
