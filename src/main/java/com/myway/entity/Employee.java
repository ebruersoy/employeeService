package com.myway.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Ebru Göksal
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "employee")
public class Employee {

    @ApiModelProperty(value = "The UUID for Employee")
    @Id
    private UUID uuid = UUID.randomUUID();

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
