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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO newEmployeeDTO){
        EmployeeDTO employeeDTO = employeeService.createEmployee(newEmployeeDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeDTO);
    }

    // PUT - UPDATE EMPLOYEE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDTO newEmployeeDTODetails){
        EmployeeDTO updatedEmployeeInfo = employeeService.updateEmployee(employeeId, newEmployeeDTODetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedEmployeeInfo);
    }

    // GET - VIEW EMPLOYEE BY EMPLOYEE ID
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> viewEmployee(@PathVariable Long employeeId){
        EmployeeDTO employee = employeeService.viewEmployee(employeeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employee);
    }

    // GET - VIEW LIST OF EMPLOYEES
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> listEmployees(){
        List<EmployeeDTO> employees = employeeService.listEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employees);
    }

    // DEL - DELETE AN EMPLOYEE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

//END OF CLASS
}
