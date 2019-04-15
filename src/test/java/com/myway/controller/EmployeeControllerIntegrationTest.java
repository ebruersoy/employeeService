package com.myway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myway.IntegrationTest;
import com.myway.dto.EmployeeRequest;
import com.myway.entity.Department;
import com.myway.entity.Employee;
import com.myway.repository.EmployeeRepository;
import com.myway.services.DepartmentService;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static com.myway.ObjectCreator.createDepartment;
import static com.myway.ObjectCreator.createEmployeeRequest;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ebru Göksal
 */
@SpringBootTest
@Category(IntegrationTest.class)
class EmployeeControllerIntegrationTest {
    private EmployeeRequest generatedEmployeeRequest;
    private Department department;


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeController employeeController;


    @Autowired
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        department = createDepartment();
        generatedEmployeeRequest = createEmployeeRequest();
    }

    @Test
    void should_save_employee() throws JsonProcessingException {
        Department savedDepartment = departmentService.save(department);
        generatedEmployeeRequest.setUuid(null);
        generatedEmployeeRequest.setDepartmentId(savedDepartment.getId());
        ResponseEntity<Employee> responseEntity = employeeController.save(generatedEmployeeRequest);
        Employee response = responseEntity.getBody();
        assertThat(response, not(nullValue()));
        assertNotNull(response.getUuid());
    }

    @Test
    void should_update_employee() throws JsonProcessingException {
        department.setName("test2");
        Department savedDepartment = departmentService.save(department);
        generatedEmployeeRequest.setUuid(null);
        generatedEmployeeRequest.setDepartmentId(savedDepartment.getId());
        ResponseEntity<Employee> responseEntity = employeeController.save(generatedEmployeeRequest);
        Employee response = responseEntity.getBody();
        generatedEmployeeRequest.setFullName("UPDATED FULL NAME");
        ResponseEntity<Employee> updatedResponse = employeeController.update(response.getUuid(), generatedEmployeeRequest);
        Employee updatedResponseBody = updatedResponse.getBody();
        assertEquals(updatedResponseBody.getFullName(),generatedEmployeeRequest.getFullName());
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }
}