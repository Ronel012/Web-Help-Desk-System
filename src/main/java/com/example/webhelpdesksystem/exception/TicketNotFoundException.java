package com.example.webhelpdesksystem.exception;

public class TicketNotFoundException extends RuntimeException{

    public TicketNotFoundException(String message){
        super(message);
    }
}
