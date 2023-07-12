package com.example.webhelpdesksystem.controller;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.model.Employee;
import com.example.webhelpdesksystem.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EmployeeController {

    final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // POST - CREATE EMPLOYEE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody Employee newEmployee){
        EmployeeDTO employeeDTO = employeeService.createEmployee(newEmployee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeDTO);
    }

    // PUT - UPDATE EMPLOYEE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long employeeId, @RequestBody Employee updatedEmployee){
        EmployeeDTO updatedEmployeeInfo = employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEmployeeInfo);
    }

    // GET - VIEW EMPLOYEE BY EMPLOYEE ID
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> viewEmployee(@PathVariable Long employeeId){
        EmployeeDTO employee = employeeService.viewEmployee(employeeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employee);
    }

    // GET - VIEW LIST OF EMPLOYEES
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> listEmployees(){
        List<EmployeeDTO> employees = employeeService.listEmployees();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employees);
    }

    // DEL - DELETE AN EMPLOYEE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") Long employeeId){

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

//END OF CLASS
}
