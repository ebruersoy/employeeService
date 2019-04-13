package com.myway.services;

import com.myway.entity.Department;
import com.myway.exception.InvalidRequestException;
import com.myway.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

/**
 * @author Ebru Göksal
 */
@Component
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department save(Department department){
        if(department.getName() == null){
            throw new InvalidRequestException("departmentName","null","not null");
        }
        if(department.getName().isEmpty()){
            throw new InvalidRequestException("departmentName","empty","not empty");
        }
        Department departmentByName = departmentRepository.findByName(department.getName());
        if(departmentByName != null){
            throw new InvalidRequestException("department",department.getName(),"not exists");
        }
        return departmentRepository.save(department);
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }
}
