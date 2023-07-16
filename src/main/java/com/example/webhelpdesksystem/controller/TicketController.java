package com.example.webhelpdesksystem.controller;

import com.example.webhelpdesksystem.dto.EmployeeDTO;
import com.example.webhelpdesksystem.dto.TicketDTO;
import com.example.webhelpdesksystem.model.Employee;
import com.example.webhelpdesksystem.model.Ticket;
import com.example.webhelpdesksystem.services.EmployeeService;
import com.example.webhelpdesksystem.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TicketController {

    final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    // GET - VIEW TICKET
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDTO> viewTicket(@PathVariable Long ticketId){
        TicketDTO ticket = ticketService.viewTicket(ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticket);
    }

    // GET - VIEW LIST OF TICKETS
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> viewListOfTickets(){
        List<TicketDTO> tickets = ticketService.listOfTickets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tickets);
    }

    // GET - LIST OF TICKETS ASSIGNED TO EMPLOYEE USING EMPLOYEE ID
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/employee/{employeeId}")
    public ResponseEntity<List<TicketDTO>> findTicketsByEmployeeId(@PathVariable Long employeeId){
        List<TicketDTO> tickets = ticketService.findTicketsByAssigneeId(employeeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tickets);
    }

    // GET - VIEW ALL WATCHERS ASSIGNED TO THE TICKET
    @PreAuthorize("hasRole('USER')")
    @GetMapping("tickets/{ticker_number}/watchers")
    public ResponseEntity<List<EmployeeDTO>> getTicketWatchers(@PathVariable Long ticker_number){
        List<EmployeeDTO> employees = ticketService.getWatchersByTicketNumber(ticker_number);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employees);
    }

    // POST  - CREATE TICKET
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/tickets")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticket){
        TicketDTO newTicket =  ticketService.createTicket(ticket);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newTicket);
    }

    // PUT  - UPDATE TICKET DETAILS
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketDTO> updateTicketDetails(@PathVariable("ticketId") Long ticketId, @RequestBody TicketDTO newTicketDetails){
        TicketDTO ticketDTO = ticketService.updateTicketDetails(ticketId, newTicketDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ticketDTO);
    }

    // DEL - DELETE A TICKET
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("ticketId") Long ticketId){
        ticketService.deleteTicket(ticketId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

//END OF CLASS
}
