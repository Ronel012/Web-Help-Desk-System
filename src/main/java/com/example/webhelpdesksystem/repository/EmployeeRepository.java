package com.example.webhelpdesksystem.repository;

import com.example.webhelpdesksystem.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Employee findByEmployeeNumber(String employeeNumber);
}
