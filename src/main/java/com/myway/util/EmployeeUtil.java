package com.myway.util;

import com.myway.dto.EmployeeRequest;
import com.myway.entity.Employee;
import com.myway.services.DepartmentService;
import org.springframework.stereotype.Component;

/**
 * @author Ebru Göksal
 */
@Component
public class EmployeeUtil {

    private DepartmentService departmentService;

    public EmployeeUtil(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public Employee convertFromRequest(EmployeeRequest employee) {
        Employee convertedEmployee = new Employee();
        convertedEmployee.setBirthday(employee.getBirthday());
        convertedEmployee.setDepartment(departmentService.findById(employee.getDepartmentId()));
        convertedEmployee.setEmail(employee.getEmail());
        convertedEmployee.setFullName(employee.getFullName());
        return convertedEmployee;
    }
}
