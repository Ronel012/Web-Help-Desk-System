package com.example.webhelpdesksystem.servicesImpl;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.dto.TicketDTO;
import com.example.webhelpdesksystem.exception.TicketDeletionException;
import com.example.webhelpdesksystem.exception.TicketNotFoundException;
import com.example.webhelpdesksystem.model.Employee;
import com.example.webhelpdesksystem.model.Ticket;
import com.example.webhelpdesksystem.repository.TicketRepository;
import com.example.webhelpdesksystem.services.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    final TicketRepository ticketRepository;
    final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper){
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    // GET - VIEW A TICKET
    @Override
    public TicketDTO viewTicket(Long ticketId) throws TicketNotFoundException{
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket id not found."));
        return this.modelMapper.map(ticket, TicketDTO.class);
    }

    // GET  - VIEW LIST OF TICKETS
    @Override
    public List<TicketDTO> listOfTickets(){
        List<Ticket> ticketList = (List<Ticket>) ticketRepository.findAll();
        TypeToken<List<TicketDTO>> ticketDTOListTypeToken = new TypeToken<>() {};
        return this.modelMapper.map(ticketList, ticketDTOListTypeToken.getType());
    }

    // GET - FIND TICKETS USING EMPLOYEE ID
    @Override
    public List<TicketDTO> findTicketsByAssigneeId(Long employeeId){
        List<Ticket> ticketList = ticketRepository.findByAssigneeId(employeeId);
        TypeToken<List<TicketDTO>> ticketDTOListTypeToken = new TypeToken<>() {};
        return this.modelMapper.map(ticketList, ticketDTOListTypeToken.getType());

    }

    // GET - FIND ALL WATCHERS ASSIGNED TO THE TICKET
    @Override
    public List<EmployeeDTO> getWatchersByTicketNumber(Long ticket_number){
        List<Employee> employeeList = ticketRepository.findWatchersByTicketNumber(ticket_number);
        TypeToken<List<EmployeeDTO>> employeeDTOListTypeToken = new TypeToken<>() {};
        return this.modelMapper.map(employeeList, employeeDTOListTypeToken.getType());
    }

    // POST - CREATE A TICKET
    @Override
    public TicketDTO createTicket(Ticket ticket){
        Ticket newTicket = ticketRepository.save(ticket);
        return this.modelMapper.map(newTicket, TicketDTO.class);
    }

    // PUT  - UPDATE TICKET INFORMATION
    @Override
    public TicketDTO updateTicketDetails(Long ticketId, Ticket newTicketDetails) throws TicketNotFoundException{
    Ticket ticketTobeUpdated = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNotFoundException("Ticket that you're about to update is not found"));

        this.modelMapper.map(newTicketDetails, ticketTobeUpdated);
        ticketRepository.save(ticketTobeUpdated);
        return this.modelMapper.map(ticketTobeUpdated, TicketDTO.class);

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
