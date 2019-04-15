package com.myway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myway.dto.EmployeeRequest;
import com.myway.entity.Employee;
import com.myway.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Ebru Göksal
 */
@Controller
@RequestMapping(path = "employee/v1")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public @ResponseBody
    ResponseEntity save(@RequestBody EmployeeRequest employeeRequest) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.save(employeeRequest));
    }

    @GetMapping(path = "{uuid}")
    @Secured("ROLE_USER")
    public @ResponseBody
    Employee findByUuid(@PathVariable("uuid") UUID uuid){
       return  employeeService.findByUuid(uuid);
    }

    @PutMapping(path = "{uuid}")
    public @ResponseBody ResponseEntity update(@PathVariable("uuid") UUID uuid, @RequestBody EmployeeRequest employee) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(uuid,employee));
    }

    @DeleteMapping(path ="{uuid}")
    public @ResponseBody ResponseEntity remove(@PathVariable("uuid")UUID uuid) throws JsonProcessingException {
        employeeService.delete(uuid);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted Successfully");
    }

}
