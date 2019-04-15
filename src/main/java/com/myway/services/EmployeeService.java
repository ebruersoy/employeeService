package com.myway.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myway.dto.EmployeeRequest;
import com.myway.entity.Department;
import com.myway.entity.Employee;
import com.myway.exception.InvalidRequestException;
import com.myway.repository.EmployeeRepository;
import com.myway.util.EmployeeUtil;
import com.myway.util.EventType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

/**
 * @author Ebru Göksal
 */
@Component
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentService departmentService;
    private EmployeeUtil employeeUtil;
    private EventService eventService;

    public EmployeeService(EmployeeRepository employeeRepository,EventService eventService,
                           DepartmentService departmentService, EmployeeUtil employeeUtil) {

        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.employeeUtil = employeeUtil;
        this.eventService = eventService;
    }

    public Employee save(EmployeeRequest employee) throws JsonProcessingException {
        Employee byEmail = employeeRepository.findByEmail(employee.getEmail());
        if(byEmail != null){
            throw new InvalidRequestException("employee", employee.getEmail(), "not existing");
        }
        checkEmployeeData(employee);
        Employee convertedEmployee = employeeUtil.convertFromRequest(employee);
        Employee saved = employeeRepository.save(convertedEmployee);
        eventService.send(EventType.ADD, saved);
        return saved;
    }

    public Employee findByUuid(UUID uuid) {
        return employeeRepository.findByUuid(uuid);
    }

    public Employee update(UUID uuid , EmployeeRequest employee) throws JsonProcessingException {
        checkEmployeeData(employee);
        if(uuid == null){
            throw new InvalidRequestException("uuid", "null", "not null");
        }
        Employee foundEmployee = findByUuid(uuid);
        if(foundEmployee == null){
            throw new InvalidRequestException("requested employee", "null", "not null");
        }
        Employee byEmail = employeeRepository.findByEmail(employee.getEmail());
        if(byEmail != null && byEmail.getUuid().compareTo(uuid) != 0){
            throw new InvalidRequestException("employee", employee.getEmail(), "not existing");
        }

        Employee converted = employeeUtil.convertFromRequest(employee);
        converted.setUuid(uuid);
        Employee saved = employeeRepository.save(converted);
        eventService.send(EventType.UPDATED,saved);
        return saved;
    }

    public void delete(UUID uuid) throws JsonProcessingException {
        if(uuid == null){
            throw new InvalidRequestException("uuid", "null", "not null");
        }
        Employee foundEmployee = findByUuid(uuid);
        if(foundEmployee == null){
            throw new InvalidRequestException("requested employee", "null", "not null");
        }
        eventService.send(EventType.DELETED,foundEmployee);
        employeeRepository.delete(foundEmployee);
    }

    private void checkEmployeeData(EmployeeRequest employee) {
        if(employee.getEmail() == null){
            throw new InvalidRequestException("email", "null", "not null");
        }
        if(employee.getEmail().isEmpty()){
            throw new InvalidRequestException("email", "empty", "not empty");
        }
        if(employee.getBirthday() == null){
            throw new InvalidRequestException("birthday", "null", "not null");
        }
        try{
            LocalDate.parse(employee.getBirthday(), DateTimeFormatter.ISO_LOCAL_DATE);
        }catch (DateTimeParseException ex){
            throw new InvalidRequestException("birthday", "invalid format", "format like '2011-12-03'");
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

    }



}
