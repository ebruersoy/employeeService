package com.myway.controller;

import com.myway.IntegrationTest;
import com.myway.entity.Department;
import com.myway.repository.DepartmentRepository;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static com.myway.ObjectCreator.createDepartment;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Ebru Göksal
 */
@SpringBootTest
@Category(IntegrationTest.class)
class DepartmentControllerIntegrationTest {
    private Department generatedDepartment;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        generatedDepartment = createDepartment();
    }

    @Test
    void should_save_department() {
        generatedDepartment.setId(null);
        ResponseEntity<Department> responseEntity = departmentController.save(generatedDepartment);
        Department response = responseEntity.getBody();
        assertThat(response, not(nullValue()));
        assertNotNull(response.getId());
    }

    @AfterEach
    void tearDown() {
        departmentRepository.deleteAll();
    }
}