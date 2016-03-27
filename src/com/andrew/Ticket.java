package com.andrew;

import java.text.DateFormat;
import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private String resolution;
    private Date dateResolved;
    private DateFormat df = DateFormat.getDateInstance();


    //STATIC Counter - accessible to all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    protected static int staticTicketIDCounter = 1;
    protected final static String REPORT_SEP = ";";
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

    protected boolean isResolved() {
        if (resolution.equals("unresolved")) {
            return false;
        } else return true;
    }


    public String toString() {
        String printStr = "ID= " + this.ticketID + " | Issue: " + this.description + " | Priority: " + this.priority + " | Reported by: "
                + this.reporter + " | Reported on: " + df.format(this.dateReported);

        if (isResolved()) {
            printStr += " / Resolution: " + this.resolution + " | Resolved on: " + this.dateResolved;
        }

        return printStr;
    }

    public String fileFormatStr() {
        String formattedStr = this.description + REPORT_SEP + this.priority + REPORT_SEP + this.reporter + REPORT_SEP + df.format(this.dateReported);

        if (isResolved()) {
            formattedStr += REPORT_SEP + this.resolution + REPORT_SEP + this.dateResolved;
        }

        return formattedStr;


    }



}

