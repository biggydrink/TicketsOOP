package com.andrew;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class TicketManager {

    protected static LinkedList<Ticket> openTickets = new LinkedList<>();
    protected static LinkedList<Ticket> resolvedTickets = new LinkedList<>();

    public static void main(String[] args) {

        // Get currently open tickets (if any)
        File savedTicketsFile = new File("open_tickets.txt");
        if (savedTicketsFile.exists() && !savedTicketsFile.isDirectory()) {
            openTickets = readTickets(savedTicketsFile, openTickets);
        }

        TicketGUI ticketGUI = new TicketGUI();



        /*

        // un-comment below for testing purposes

        Ticket ticket1 = new Ticket("Big Fire!",5,"Andrew",new Date());
        Ticket ticket2 = new Ticket("Small fire",3,"Andrew",new Date());
        Ticket ticket3 = new Ticket("Tiny fire",1,"Andrew",new Date());
        Ticket ticket4 = new Ticket("Water balloon!",5,"Andrew",new Date());
        Ticket ticket5 = new Ticket("Squirt gun (water)",2,"Andrew",new Date());
        Ticket ticket6 = new Ticket("Spilled drink (water)",4,"Andrew",new Date());

        openTickets.add(ticket1);
        openTickets.add(ticket2);
        openTickets.add(ticket3);
        openTickets.add(ticket4);
        openTickets.add(ticket5);
        openTickets.add(ticket6);

        */
    }


    private static LinkedList<Ticket> readTickets(File fileName, LinkedList<Ticket> ticketQueue) {

        DateFormat df = DateFormat.getDateInstance();
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
            // Import ticket counter (top of open tickets file)
            String topLine = bufReader.readLine();
            Ticket.staticTicketIDCounter = Integer.parseInt(topLine);
            // Loop through remaining tickets in file and add to ticketQueue
            for (String line = bufReader.readLine(); line != null; line = bufReader.readLine()) {
                String[] params = line.split(Ticket.REPORT_SEP);

                String description = params[0];
                int priority = Integer.parseInt(params[1]);
                String reporter = params[2];
                try {
                    Date createDate = df.parse(params[3]);
                    ticketQueue.add(new Ticket(description, priority, reporter));
                } catch (ParseException pe) {
                    System.out.println("Error loading date from ticket " + description);
                    System.out.println("Adding ticket with today's date");
                    ticketQueue.add(new Ticket(description, priority, reporter));
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error loading ticket queue from " + fileName + ": " + ioe);
        }

        return ticketQueue;
    }

    protected static void saveTickets(LinkedList<Ticket> ticketQueue, String fileName) {

        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(fileName));
            if (fileName.equals("open_tickets.txt")) {
                bufWriter.write(Integer.toString(Ticket.staticTicketIDCounter));
                bufWriter.newLine();
            }
            for (Ticket ticket : ticketQueue) {
                bufWriter.write(ticket.fileFormatStr());
                bufWriter.newLine();
            }
            bufWriter.close();
        } catch (IOException ioe) {
            System.out.println("Error saving tickets: " + ioe);
        }


    }


    /* Gets a list of tickets that have a searched string in their description */
    protected static LinkedList<Ticket> searchByName(LinkedList<Ticket> ticketQueue, String query) {

        //System.out.println("Enter search query:");
        //String query = getStringInput().toLowerCase();

        LinkedList<Ticket> searchResults = new LinkedList<Ticket>();

        for (Ticket ticket : ticketQueue) {
            String desc = ticket.getDescription().toLowerCase();
            if (desc.contains(query)) {
                searchResults.add(ticket);
            }
        }

        return searchResults;

    }
}