package com.example.webhelpdesksystem.mapper;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    // EMPLOYEE TO DTO
    EmployeeDTO mapEmployeeToDTO(Employee employee);

    // DTO TO EMPLOYEE
    Employee mapDTOToEmployee(EmployeeDTO employeeDTO);

    // UPDATE EMPLOYEE DETAILS
    @Mapping(target="id", ignore = true)
    void updateEmployeeDetails(EmployeeDTO newEmployeeDTODetails, @MappingTarget Employee employee);

    // FROM LIST EMPLOYEE TO LIST DTO
    List<EmployeeDTO> updateListEmployeeToDTO(List<Employee> employeeList);
}
