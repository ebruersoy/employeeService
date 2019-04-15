package com.myway.services;

import com.myway.UnitTest;
import com.myway.entity.Department;
import com.myway.exception.InvalidRequestException;
import com.myway.repository.DepartmentRepository;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.myway.ObjectCreator.createDepartment;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ebru Göksal
 */
@SpringBootTest
@Category(UnitTest.class)
class DepartmentServiceUnitTest {

    private Department generatedDepartment;
    private DepartmentService departmentService = new DepartmentService(null);

    @Autowired
    private DepartmentRepository departmentRepo;

    @BeforeEach
    void setUp() {
        generatedDepartment = createDepartment();
    }

    @Test
    void should_expect_exception_on_save_with_null_name() {
        generatedDepartment.setName(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            departmentService.save(generatedDepartment);
        });
    }

    @Test
    void should_expect_exception_on_save_with_empty_name() {
        generatedDepartment.setName("");
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            departmentService.save(generatedDepartment);
        });
    }

    @Test
    void should_expect_exception_on_save_with_same_name() {
        DepartmentRepository departmentRepository = Mockito.mock(DepartmentRepository.class);
        Mockito.when(departmentRepository.findByName(ArgumentMatchers.anyString())).then(invocationOnMock -> generatedDepartment);
        departmentService = new DepartmentService(departmentRepository);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            departmentService.save(generatedDepartment);
        });
    }

    @Test
    void should_find_by_id() {
        DepartmentRepository departmentRepository = Mockito.mock(DepartmentRepository.class);
        Mockito.when(departmentRepository.save(ArgumentMatchers.any())).then(invocationOnMock -> generatedDepartment );
        departmentService = new DepartmentService(departmentRepository);
        Department saved = departmentService.save(generatedDepartment);
        assertEquals(saved.getName(),generatedDepartment.getName());

    }

    @AfterEach
    void tearDown(){
        departmentRepo.deleteAll();
    }
}