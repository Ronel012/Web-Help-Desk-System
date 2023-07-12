package com.example.webhelpdesksystem.servicesImpl;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.exception.EmployeeNotFoundException;
import com.example.webhelpdesksystem.exception.EmployeeWithTicketDeletionException;
import com.example.webhelpdesksystem.model.Employee;
import com.example.webhelpdesksystem.model.Ticket;
import com.example.webhelpdesksystem.repository.EmployeeRepository;
import com.example.webhelpdesksystem.repository.TicketRepository;
import com.example.webhelpdesksystem.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    final EmployeeRepository employeeRepository;
    final TicketRepository ticketRepository;
    final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                                TicketRepository ticketRepository,
                                ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    // GET - VIEW AN EMPLOYEE
    @Override
    public EmployeeDTO viewEmployee(Long employeeId) throws EmployeeNotFoundException{
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(()-> new EmployeeNotFoundException("Employee not found."));
        return this.modelMapper.map(employee, EmployeeDTO.class);
    }

    // GET - LIST OF EMPLOYEES
    @Override
    public List<EmployeeDTO> listEmployees() {
        List<Employee> employeeList = (List<Employee>) employeeRepository.findAll();
        // from compile time to run time
        TypeToken<List<EmployeeDTO>> employeeDTOListTypeToken = new TypeToken<>() {};
        return this.modelMapper.map(employeeList, employeeDTOListTypeToken.getType());
    }

    // POST - CREATE EMPLOYEE
    @Override
    public EmployeeDTO createEmployee(Employee newEmployee) {
        Employee employee = employeeRepository.save(newEmployee);
        return this.modelMapper.map(employee, EmployeeDTO.class);
    }

    // PUT  - UPDATE AN EMPLOYEE
    @Override
    public EmployeeDTO updateEmployee(Long employeeId, Employee newEmployeeDetails) throws EmployeeNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee ID: " + employeeId + " not found"));

        // PROCESS TO UPDATE THE OLD EMPLOYEE DETAILS
        this.modelMapper.map(newEmployeeDetails,employee);

        // PROCESS TO SAVE DATA TO DATABASE
        employeeRepository.save(employee);

        return this.modelMapper.map(employee, EmployeeDTO.class);
    }

    //  DELETE - DELETE A TICKET
    @Override
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException, EmployeeWithTicketDeletionException{

        // CHECK IF EMPLOYEE EXIST
        Employee employeeToBeDeleted = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));

        // THROW EXCEPTION IF EMPLOYEE HAS ASSIGNED TICKET, CANNOT BE DELETED
        if (hasAssignedTicket(employeeId)){
            String deleteMessageFailed = "Employee ID: " + employeeId + " cannot be deleted due to assigned tickets.";
            throw new EmployeeWithTicketDeletionException(deleteMessageFailed);
        }

        // DELETE AN EMPLOYEE WITHOUT ASSIGNED TICKET
        employeeRepository.deleteById(employeeId);
    }

    //CHECKING IF AN EMPLOYEE HAS ASSIGNED TICKET
    public boolean hasAssignedTicket(Long employeeId){
        List<Ticket> tickets = ticketRepository.findByAssigneeId(employeeId);
        return !tickets.isEmpty();
    }


//END OF CLASS
}
