package com.myway.controller;

import com.myway.controller.DepartmentController;
import com.myway.entity.Department;
import com.myway.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ebru Göksal
 */
@SpringBootTest
class DepartmentControllerTest {
    private Department generatedDepartment;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        generatedDepartment = new Department();
        generatedDepartment.setId(1L);
        generatedDepartment.setName("Test Department");
    }

    @Test
    void should_save_department() {
        generatedDepartment.setId(null);
        ResponseEntity<Department> responseEntity = departmentController.save(generatedDepartment);
        Department resppnse = responseEntity.getBody();
        assertThat(resppnse, not(nullValue()));
        assertNotNull(resppnse.getId());
    }

    @AfterEach
    void tearDown() {
        departmentRepository.deleteAll();
    }
}