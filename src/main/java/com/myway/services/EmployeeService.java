package com.myway.services;

import com.myway.dto.EmployeeRequest;
import com.myway.entity.Department;
import com.myway.entity.Employee;
import com.myway.exception.InvalidRequestException;
import com.myway.repository.EmployeeRepository;
import com.myway.util.EmployeeUtil;
import org.springframework.stereotype.Component;

/**
 * @author Ebru Göksal
 */
@Component
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeUtil employeeUtil;
    private DepartmentService departmentService;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeUtil employeeUtil, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.employeeUtil = employeeUtil;
        this.departmentService = departmentService;
    }

    private Employee save(EmployeeRequest employee){
        if(employee.getEmail() == null){
            throw new InvalidRequestException("email", "null", "not null");
        }
        if(employee.getEmail().isEmpty()){
            throw new InvalidRequestException("email", "empty", "not empty");
        }
        if(employee.getBirthday() == null){
            throw new InvalidRequestException("birthday", "null", "not null");
        }
        if(employee.getFullName() == null){
            throw new InvalidRequestException("fullname", "null", "not null");
        }
        if(employee.getFullName().isEmpty()){
            throw new InvalidRequestException("fullname", "empty", "not empty");
        }
        if(employee.getDepartmentId() == null){
            throw new InvalidRequestException("departmentId", "null", "not null");
        }
        if(employee.getDepartmentId() == 0){
            throw new InvalidRequestException("departmentId", "0", "bigget than 0");
        }
        Department department = departmentService.findById(employee.getDepartmentId());
        if(department == null){
            throw new InvalidRequestException("departmentId", employee.getDepartmentId(), "exist");
        }
        Employee byEmail = employeeRepository.findByEmail(employee.getEmail());
        if(byEmail != null){
            throw new InvalidRequestException("employee", employee.getEmail(), "not existing");
        }
        return employeeRepository.save(employeeUtil.convertFromRequest(employee));
    }

}
