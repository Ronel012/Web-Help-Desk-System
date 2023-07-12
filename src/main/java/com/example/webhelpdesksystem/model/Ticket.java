package com.example.webhelpdesksystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticket_number;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    //Low, Normal, Major, Critical = ENUM
    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private Severity severity;

    //New, Assigned, In-Progress, Closed = ENUM
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    //assignee is of type "Employee"
    @ManyToOne()
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    private Employee assignee;


    //watchers is a list of type "Employee"
    @ManyToMany()
    @JoinTable(
            name = "ticket_watchers",
            joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_number"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    private List<Employee> watchers;

    // JPA WILL SAVE DATA FROM ENUM TYPE TO STRING TYPE
    // JPA WILL RETURN THE TYPE FROM STRING TO ENUM TYPE WHEN RETRIEVED
    // CONSTANT SEVERITY
    private enum Severity {
        LOW,
        NORMAL,
        MAJOR,
        CRITICAL
    }

    // CONST STATUS
    //New, Assigned, In-Progress, Closed = ENUM
    private enum Status {
        NEW,
        ASSIGNED,
        IN_PROGRESS,
        CLOSED
    }
}
