package com.example.webhelpdesksystem.dto;

import com.example.webhelpdesksystem.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDTO {


    private Long ticket_number;
    private String title;
    private String description;

    //Low, Normal, Major, Critical = ENUM
    private String severity;

    //New, Assigned, In-Progress, Closed = ENUM
    private String status;

    //assignee is of type "Employee"
    private Employee assignee;

    //watchers is a list of type "Employee"
    private List<Employee> watchers;

    private enum Severity {
        LOW,
        NORMAL,
        MAJOR,
        CRITICAL
    }

    // CONST STATUS
    private enum Status {
        NEW,
        ASSIGNED,
        IN_PROGRESS,
        CLOSED
    }
}
