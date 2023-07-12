package com.example.webhelpdesksystem.exceptionhandler;

import com.example.webhelpdesksystem.exception.EmployeeNotFoundException;
import com.example.webhelpdesksystem.exception.EmployeeWithTicketDeletionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class EmployeeExceptionHandler {

    // CANNOT DELETE AN EMPLOYEE WITH ASSIGNED TICKET
    // RETURN A RESPONSE ENTITY OBJECT
    @ExceptionHandler(EmployeeWithTicketDeletionException.class)
    public ResponseEntity<String> handleEmployeeWithTicketDeletionException(EmployeeWithTicketDeletionException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // EMPLOYEE NOT FOUND EXCEPTION
    // RETURN A RESPONSE ENTITY OBJECT
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    // EMPLOYEE CANNOT DELETE WITH TICKET EXCEPTION
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleEmployeeCannotDeleteWithTicketException(DataIntegrityViolationException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }


}
