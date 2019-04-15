package com.myway.services;

import com.myway.IntegrationTest;
import com.myway.dto.EmployeeRequest;
import com.myway.entity.Department;
import com.myway.entity.Employee;
import com.myway.repository.DepartmentRepository;
import com.myway.repository.EmployeeRepository;
import com.myway.util.EmployeeUtil;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.myway.ObjectCreator.createDepartment;
import static com.myway.ObjectCreator.createEmployeeRequest;
import static junit.framework.TestCase.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ebru Göksal
 */
@SpringBootTest
@Category(IntegrationTest.class)
class EmployeeServiceIntegrationTest {
    private EmployeeRequest employeeRequest;
    private static Department department;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeUtil util;

    @BeforeAll
    public static void init(){
        department = createDepartment();
    }

    @BeforeEach
    void setUp() {
        employeeRequest = createEmployeeRequest();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    @Transactional
    void save() {
        DepartmentService departmentService = new DepartmentService(departmentRepository);
        Department savedDepartment = departmentService.save(department);
        employeeRequest.setDepartmentId(savedDepartment.getId());
        Employee saved = repository.save(util.convertFromRequest(employeeRequest));
        assertNotNull(saved);
    }

    @Test
    @Transactional
    void findByUuid() {
        DepartmentService departmentService = new DepartmentService(departmentRepository);
        Department savedDepartment = departmentService.save(department);
        employeeRequest.setDepartmentId(savedDepartment.getId());
        Employee saved = repository.save(util.convertFromRequest(employeeRequest));
        assertNotNull(saved);
        assertNotNull(employeeService.findByUuid(saved.getUuid()));
    }

    @Test
    void update() {
        DepartmentService departmentService = new DepartmentService(departmentRepository);
        Department savedDepartment = departmentService.save(department);
        employeeRequest.setDepartmentId(savedDepartment.getId());
        Employee saved = repository.save(util.convertFromRequest(employeeRequest));
        saved.setFullName("Name Updated");
        Employee updated =repository.save(saved);
        assertEquals(updated.getFullName(),"Name Updated");
    }

    @Test
    void remove() {
        DepartmentService departmentService = new DepartmentService(departmentRepository);
        Department savedDepartment = departmentService.save(department);
        employeeRequest.setDepartmentId(savedDepartment.getId());
        Employee saved = repository.save(util.convertFromRequest(employeeRequest));
        repository.delete(saved);
        assertNull(repository.findByUuid(saved.getUuid()));
    }
}