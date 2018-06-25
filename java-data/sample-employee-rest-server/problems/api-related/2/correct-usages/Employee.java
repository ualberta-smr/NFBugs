package com.derrikcurran.sample.employeerestserver.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    
}
