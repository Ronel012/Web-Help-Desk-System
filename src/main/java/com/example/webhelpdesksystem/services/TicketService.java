package com.example.webhelpdesksystem.services;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.dto.TicketDTO;
import com.example.webhelpdesksystem.exception.TicketDeletionException;
import com.example.webhelpdesksystem.exception.TicketNotFoundException;
import com.example.webhelpdesksystem.model.Employee;
import com.example.webhelpdesksystem.model.Ticket;
import java.util.List;

public interface TicketService {

    // GET - VIEW A TICKET
    TicketDTO viewTicket(Long ticketId) throws TicketNotFoundException;

    // GET - LIST OF TICKETS
    List<TicketDTO> listOfTickets() throws TicketNotFoundException;;

    // GET - ALL TICKETS ASSIGNED TO EMPLOYEE
    List<TicketDTO> findTicketsByAssigneeId(Long employeeId) throws TicketNotFoundException;;

    // GET - ALL WATCHERS ASSIGNED TO THE TICKET
    List<EmployeeDTO> getWatchersByTicketNumber(Long ticket_number);

    // POST - CREATE TICKET
    TicketDTO createTicket(TicketDTO ticketDTO);

    // PUT - UPDATE TICKET
    TicketDTO updateTicketDetails(Long ticketId, TicketDTO newTicketDetails) throws TicketNotFoundException;

    // DEL - DELETE  TICKET
    void deleteTicket(Long ticketId) throws TicketNotFoundException;

// END OF CLASS
}
