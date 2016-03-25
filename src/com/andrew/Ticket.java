package com.andrew;

import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private String resolution;
    private Date dateResolved;

    //STATIC Counter - accessible to all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;

    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.resolution = "unresolved";
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }

    protected void setResolution(String resolution) {
        this.resolution = resolution;
    }

    protected void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    protected int getPriority() {
        return priority;
    }

    protected int getTicketID() {
        return ticketID;
    }

    protected String getDescription() {
        return description;
    }

    protected String getReporter() {
        return reporter;
    }

    protected Date getDateReported() {
        return dateReported;
    }

    public String toString(){
        String printStr = "ID= " + this.ticketID + " | Issued: " + this.description + " | Priority: " + this.priority + " | Reported by: "
                + this.reporter + " | Reported on: " + this.dateReported;

        if (!this.resolution.equalsIgnoreCase("unresolved")) {
            printStr += " | Resolution: " + this.resolution + " | Resolved on: " + this.dateResolved;
        }

        return printStr;
    }



}

