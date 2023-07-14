package com.example.webhelpdesksystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data // for getter and setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    // JPA WILL SAVE DATA FROM ENUM TYPE TO STRING TYPE
    // JPA WILL RETURN THE TYPE FROM STRING TO ENUM TYPE WHEN RETRIEVED
    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    // CONSTANT ROLES
    public enum Department {
        IT,
        ADMIN,
        HR,
        SALES
    }

// END OF CLASS
}


