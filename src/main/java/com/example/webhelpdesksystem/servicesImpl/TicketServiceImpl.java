package com.example.webhelpdesksystem.servicesImpl;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.dto.TicketDTO;
import com.example.webhelpdesksystem.exception.TicketDeletionException;
import com.example.webhelpdesksystem.exception.TicketNotFoundException;
import com.example.webhelpdesksystem.mapper.TicketMapper;
import com.example.webhelpdesksystem.model.Employee;
import com.example.webhelpdesksystem.model.Ticket;
import com.example.webhelpdesksystem.repository.EmployeeRepository;
import com.example.webhelpdesksystem.repository.TicketRepository;
import com.example.webhelpdesksystem.services.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final EmployeeRepository employeeRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository){
        this.ticketRepository = ticketRepository;
        this.ticketMapper = TicketMapper.INSTANCE;
        this.employeeRepository = employeeRepository;
    }

    // GET - VIEW A TICKET
    @Override
    public TicketDTO viewTicket(Long ticketId) throws TicketNotFoundException{
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket id not found."));
        return this.ticketMapper.ticketToDTO(ticket);
    }

    // GET  - VIEW LIST OF TICKETS
    @Override
    public List<TicketDTO> listOfTickets(){
        List<Ticket> ticketList = (List<Ticket>) ticketRepository.findAll();
        return this.ticketMapper.listTicketsToDTO(ticketList);
    }

    // GET - FIND TICKETS USING EMPLOYEE ID
    @Override
    public List<TicketDTO> findTicketsByAssigneeId(Long employeeId){
        List<Ticket> ticketList = ticketRepository.findByAssigneeId(employeeId);
        return this.ticketMapper.listTicketsToDTO(ticketList);
    }

    // GET - FIND ALL WATCHERS ASSIGNED TO THE TICKET
    @Override
    public List<EmployeeDTO> getWatchersByTicketNumber(Long ticket_number){
        List<Employee> employeeList = ticketRepository.findWatchersByTicketNumber(ticket_number);
        return this.ticketMapper.listEmployeeToDTO(employeeList);
    }

    // POST - CREATE A TICKET
    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO){
        Ticket ticket = this.ticketMapper.dtoToTicket(ticketDTO);

        //fetch the assignee
        Employee assignee = employeeRepository.findById(ticket.getAssignee().getId()).orElse(null);

        //fetch the watchers
        List<Employee> listOfWatchers = new ArrayList<>();
        List<Employee> watchers = ticketDTO.getWatchers();
        for (Employee watcher : watchers){
            listOfWatchers.add(employeeRepository.findById(watcher.getId()).orElse(null));
        }

        // set assignee and watchers
        ticket.setAssignee(assignee);
        ticket.setWatchers(listOfWatchers);

        return this.ticketMapper.ticketToDTO(ticketRepository.save(ticket));
    }

    // PUT  - UPDATE TICKET INFORMATION
    @Override
    public TicketDTO updateTicketDetails(Long ticketId, TicketDTO newTicketDetails) throws TicketNotFoundException{
    Ticket ticketTobeUpdated = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException("Ticket that you're about to update is not found"));

        this.ticketMapper.updateTicketDetails(newTicketDetails, ticketTobeUpdated);
        ticketRepository.save(ticketTobeUpdated);
        return this.ticketMapper.ticketToDTO(ticketTobeUpdated);
    }

    // DEL - DELETE A TICKET
    @Override
    public void deleteTicket(Long ticketId) throws TicketNotFoundException {
        Ticket ticketToBeDeleted = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket id not found."));

        ticketRepository.deleteById(ticketId);
    }

//END OF CLASS
}
