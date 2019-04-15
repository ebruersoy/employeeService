package com.myway;

import com.myway.dto.EmployeeRequest;
import com.myway.entity.Department;
import com.myway.entity.Employee;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Ebru Göksal
 **/
public class ObjectCreator {
    private static final String uuid = "b4323058-d431-4788-9ce2-14aaf7a265bd";

    public static EmployeeRequest createEmployeeRequest() {
        EmployeeRequest generatedEmployeeRequest = new EmployeeRequest();
        generatedEmployeeRequest.setBirthday("2019-08-08");
        generatedEmployeeRequest.setDepartmentId(1L);
        generatedEmployeeRequest.setEmail("testemail@email.com");
        generatedEmployeeRequest.setFullName("Test Employee");
        return generatedEmployeeRequest;
    }

    public static Employee createEmployee() {
        Employee generatedEmployee = new Employee();
        generatedEmployee.setFullName(generatedEmployee.getFullName());
        generatedEmployee.setBirthday(LocalDate.now());
        generatedEmployee.setDepartment(new Department());
        generatedEmployee.setEmail(generatedEmployee.getEmail());
        generatedEmployee.setUuid(UUID.fromString(uuid));
        return generatedEmployee;
    }

    public static Department createDepartment() {
        Department department = new Department();
        department.setName("test");
        return department;
    }
}
