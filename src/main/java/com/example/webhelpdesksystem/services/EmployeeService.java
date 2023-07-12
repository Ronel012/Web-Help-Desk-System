package com.example.webhelpdesksystem.services;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.exception.EmployeeNotFoundException;
import com.example.webhelpdesksystem.exception.EmployeeWithTicketDeletionException;
import com.example.webhelpdesksystem.model.Employee;
import java.util.List;

public interface EmployeeService {

    // POST - CREATE NEW EMPLOYEE
    EmployeeDTO createEmployee(Employee newEmployee);

    // GET - VIEW AN EMPLOYEE
    EmployeeDTO viewEmployee(Long employeeId) throws EmployeeNotFoundException;

    // GET - LIST OF AN EMPLOYEE
    List<EmployeeDTO> listEmployees();

    // PUT - UPDATE EMPLOYEE
    EmployeeDTO updateEmployee(Long employeeId, Employee updatedEmployee) throws EmployeeNotFoundException;

    // DEL - DELETE AN EMPLOYEE
    void deleteEmployee(Long employeeId) throws EmployeeWithTicketDeletionException, EmployeeNotFoundException;

//END OF CLASS
}
