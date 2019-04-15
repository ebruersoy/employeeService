package com.myway.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myway.UnitTest;
import com.myway.dto.EmployeeRequest;
import com.myway.entity.Department;
import com.myway.entity.Employee;
import com.myway.exception.InvalidRequestException;
import com.myway.kafka.producer.Sender;
import com.myway.repository.EmployeeRepository;
import com.myway.util.EmployeeUtil;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.myway.ObjectCreator.createEmployee;
import static com.myway.ObjectCreator.createEmployeeRequest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;

/**
 * @author Ebru Göksal
 */
@SpringBootTest
@Category(UnitTest.class)
class EmployeeServiceUnitTest {

    private static EmployeeRequest generatedEmployee;
    private static Employee employee;
    private static DepartmentService departmentService;
    private static EmployeeRepository repository;
    private static final String uuid = "b4323058-d431-4788-9ce2-14aaf7a265bd";

    @Autowired
    private static EmployeeUtil employeeUtil;

    @Autowired
    private static EventService eventService;

    private EmployeeService employeeService;

    @BeforeEach
    void init(){
        employee = createEmployee();

        repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findByUuid(any())).then(invocation -> employee);
        Mockito.when(repository.save(any())).then(invocation -> employee);
        departmentService = Mockito.mock(DepartmentService.class);
        employeeUtil = new EmployeeUtil(departmentService);
        eventService = new EventService();
        Sender sender = Mockito.mock(Sender.class);
        doAnswer((i) -> {
            return null;
        }).when(sender).send(any());
        eventService.setTemplate(sender);
        Mockito.when(departmentService.findById(anyLong())).then(invocation ->  new Department());
        employeeService = new EmployeeService(repository,eventService,departmentService,employeeUtil);
    }

    @BeforeEach
    void setUp(){
        generatedEmployee = createEmployeeRequest();

    }

    @Test
    void should_save() throws JsonProcessingException {
        Employee saved = employeeService.save(generatedEmployee);
        assertNotNull(saved);
    }

    @Test
    void should_expect_exception_on_save_with_null_name() {
        generatedEmployee.setFullName(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_empty_name() {
        generatedEmployee.setFullName("");
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_null_mail() {
        generatedEmployee.setEmail(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_empty_mail() {
        generatedEmployee.setEmail("");
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_existing_mail() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findByEmail(any())).then(invocationOnMock -> employee);
        employeeService = new EmployeeService(repository,eventService,departmentService,employeeUtil);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_null_birthday() {
        generatedEmployee.setBirthday(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_invalid_birthday() {
        generatedEmployee.setBirthday("21");
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_null_department() {
        generatedEmployee.setDepartmentId(null);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_save_with_zeroId_department() {
        generatedEmployee.setDepartmentId(0L);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.save(generatedEmployee);
        });
    }


    @Test
    void should_update() throws JsonProcessingException {
        generatedEmployee.setUuid(uuid);
        Employee updated = employeeService.update(UUID.fromString(uuid), generatedEmployee);
        assertNotNull(updated);
    }

    @Test
    void should_expect_exception_on_update_with_null_uuid()  {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.update(null,generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_update_with_null_department()  {
        Mockito.when(departmentService.findById(anyLong())).then(invocation -> null);
        employeeService = new EmployeeService(repository,eventService,departmentService,employeeUtil);        generatedEmployee.setUuid(uuid);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.update(UUID.fromString(uuid),generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_update_with_existing_mail()  {
        Employee employeeWithSameMail = new Employee();
        employeeWithSameMail.setFullName(employee.getFullName());
        employeeWithSameMail.setDepartment(employee.getDepartment());
        employeeWithSameMail.setUuid(UUID.fromString("6e5d9762-0dfc-4a53-9052-b9c94ed26e5b"));
        employeeWithSameMail.setEmail(employee.getEmail());
        employeeWithSameMail.setBirthday(employee.getBirthday());
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findByEmail(any())).then(invocationOnMock -> employeeWithSameMail);
        Mockito.when(repository.findByUuid(any())).then(invocation -> employee);
        employeeService = new EmployeeService(repository,eventService,departmentService,employeeUtil);
        generatedEmployee.setUuid(uuid);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.update(UUID.fromString(uuid),generatedEmployee);
        });
    }

    @Test
    void should_expect_exception_on_update_with_non_employee()  {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findByUuid(any())).then(invocation -> null);
        employeeService = new EmployeeService(repository,eventService,departmentService,employeeUtil);
        generatedEmployee.setUuid(uuid);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.update(UUID.fromString(uuid),generatedEmployee);
        });
    }
    @Test
    void should_expect_exception_on_delete() throws JsonProcessingException {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findByUuid(any())).then(invocation -> null);
        generatedEmployee.setUuid(uuid);
        employeeService = new EmployeeService(repository,eventService,departmentService,employeeUtil);
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.delete(UUID.fromString(uuid));
        });
    }

    @Test
    void should_expect_exception_on_delete_with_null_uuid() throws JsonProcessingException {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            employeeService.delete(null);
        });
    }

}