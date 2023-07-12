package com.example.webhelpdesksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private Long id;
    private String employeeNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Department department;

    // CONSTANT ROLES
    private enum Department {
        IT,
        ADMIN,
        HR,
        SALES
    }

}
