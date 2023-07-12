package com.example.webhelpdesksystem.repository;

import com.example.webhelpdesksystem.model.Employee;
import com.example.webhelpdesksystem.model.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findByAssigneeId(Long employeeId);

   @Query("SELECT t.watchers FROM Ticket t WHERE t.ticket_number = :ticket_number")
    List<Employee> findWatchersByTicketNumber(Long ticket_number);
}
