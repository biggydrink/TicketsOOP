package com.andrew;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class TicketManager {

    public static Scanner scanner;

    public static void main(String[] args) {

        LinkedList<Ticket> ticketQueue = new LinkedList<>();
        LinkedList<Ticket> resolvedTickets = new LinkedList<>();

        scanner = new Scanner(System.in); // global scanner

        File savedTicketsFile = new File("open_tickets.txt");
        if (savedTicketsFile.exists() && !savedTicketsFile.isDirectory()) {
            ticketQueue = readTickets(savedTicketsFile,ticketQueue);
        }

        /*

        un-comment for testing purposes

        Ticket ticket1 = new Ticket("Big Fire!",5,"Andrew",new Date());
        Ticket ticket2 = new Ticket("Small fire",3,"Andrew",new Date());
        Ticket ticket3 = new Ticket("Tiny fire",1,"Andrew",new Date());
        Ticket ticket4 = new Ticket("Water balloon!",5,"Andrew",new Date());
        Ticket ticket5 = new Ticket("Squirt gun (water)",2,"Andrew",new Date());
        Ticket ticket6 = new Ticket("Spilled drink (water)",4,"Andrew",new Date());

        ticketQueue.add(ticket1);
        ticketQueue.add(ticket2);
        ticketQueue.add(ticket3);
        ticketQueue.add(ticket4);
        ticketQueue.add(ticket5);
        ticketQueue.add(ticket6);

        */

        while(true){

            System.out.println("1. Enter Ticket\n2. Delete Ticket by ID\n3. Delete Ticket by Issue\n4. Search by Name\n5. Display All Tickets\n6. Quit");
            int task = getPositiveIntInput();

            if (task == 1) {
                // Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);

            } else if (task == 2) {
                // Delete a ticket by ID
                deleteTicketByID(ticketQueue,resolvedTickets);
            } else if (task == 3) {
                // Delete a ticket by issue (search, then show results and option to delete)
                LinkedList<Ticket> results;
                results = searchByName(ticketQueue);

                deleteTicketByID(results,resolvedTickets);
            } else if (task == 4) {
                // Search descriptions of tickets
                LinkedList<Ticket> results;
                results = searchByName(ticketQueue);

                if (results.isEmpty()) {
                    System.out.println("No results for your search");
                } else {
                    System.out.println("Search results:");
                    printAllTickets(results,"matching");
                }
            } else if (task == 5) {
                // Print all tickets
                printAllTickets(ticketQueue,"open");
                printAllTickets(resolvedTickets,"resolved");
            } else if ( task == 6 ) {
                //Quit. Future prototype may want to save all tickets to a file
                System.out.println("Quitting program");
                break;
            }
            // Any other number ignored, just brings up menu again
        }

        // Clean up (save tickets & close scanner)
        System.out.println("Saving tickets..");
        saveTickets(ticketQueue,"open_tickets.txt");
        Calendar today = Calendar.getInstance();
        String resolvedFileName = "Resolved_tickets_" +
                (today.get(Calendar.MONTH)+1) + "." + today.get(Calendar.DAY_OF_MONTH) + "." + today.get(Calendar.YEAR) + ".txt";
        saveTickets(resolvedTickets,resolvedFileName);
        scanner.close();
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
                    ticketQueue.add(new Ticket(description,priority,reporter,createDate));
                } catch (ParseException pe) {
                    System.out.println("Error loading date from ticket " + description);
                    System.out.println("Adding ticket with today's date");
                    ticketQueue.add(new Ticket(description,priority,reporter,new Date()));
                }
            }
        } catch (IOException ioe) {
            //System.out.println("Error loading ticket queue from " + fileName + ": " + ioe);
        }

        return ticketQueue;
    }

    private static void saveTickets(LinkedList<Ticket> ticketQueue,String fileName) {

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
    private static LinkedList<Ticket> searchByName(LinkedList<Ticket> ticketQueue) {

        System.out.println("Enter search query:");
        String query = getStringInput().toLowerCase();

        LinkedList<Ticket> searchResults = new LinkedList<Ticket>();

        for (Ticket ticket : ticketQueue) {
            String desc = ticket.getDescription().toLowerCase();
            if (desc.contains(query)) {
                searchResults.add(ticket);
            }
        }

        return searchResults;

    }

    /* Deletes tickets, identified by their Ticket ID */
    protected static void deleteTicketByID(LinkedList<Ticket> ticketQueue,LinkedList<Ticket> resolvedTickets) {
        printAllTickets(ticketQueue,"open");   //display list for user

        if (ticketQueue.size() == 0) {    //no tickets!
            System.out.println("No tickets to delete!\n");
            return;
        }

        //Loop over all tickets. Delete the one with this ticket ID
        boolean found = false;

        while (!found) {
            System.out.println("Enter ID of ticket to delete");
            int deleteID = getPositiveIntInput();
            for (Ticket ticket : ticketQueue) {
                if (ticket.getTicketID() == deleteID) {
                    found = true;
                    System.out.println("Ticket " + ticket.getTicketID() + ": " + ticket);
                    System.out.println("Enter resolution: ");
                    String resolution = getStringInput();
                    // Add resolved variables
                    ticket.setResolution(resolution);
                    ticket.setDateResolved(new Date());
                    // Remove from ticketQueue and add to resolvedTickets
                    ticketQueue.remove(ticket);
                    resolvedTickets.add(ticket);
                    // Output to console
                    System.out.println(String.format("Ticket %d deleted", deleteID));
                    break; //don't need loop any more.
                }
            }
            if (found == false) {
                System.out.println("Ticket ID not found, no ticket deleted");
                int response = 0;
                while (response < 1 || response > 2) {
                    System.out.println("Enter another ticket ID?");
                    System.out.println("1. Yes\n2. No");
                    response = getPositiveIntInput();
                    if (response < 1 || response > 2) {
                        System.out.println("Please enter 1 or 2");
                    } else if (response == 2) {
                        found = true;
                    }
                }
            }
        }

        printAllTickets(ticketQueue,"open");  //print updated list
    }

    /* Prints all tickets, either Open or Resolved */
    protected static void printAllTickets(LinkedList<Ticket> tickets, String ticketType) {
        System.out.println(" ------- All " + ticketType + " tickets ----------");

        for (Ticket t : tickets ) {
            System.out.println(t);
            //println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");

    }

    /* Adds tickets to the ticket queue */
    protected static void addTickets(LinkedList<Ticket> ticketQueue) {
        boolean moreProblems = true;
        String description, reporter;
        Date dateReported = new Date(); //Default constructor creates date with current date/time
        int priority;

        while (moreProblems){
            System.out.println("Enter problem");
            description = getStringInput();
            System.out.println("Who reported this issue?");
            reporter = getStringInput();
            System.out.println("Enter priority of " + description);
            priority = getPositiveIntInput();

            Ticket t = new Ticket(description, priority, reporter, dateReported);
            addTicketInPriorityOrder(ticketQueue, t);

            printAllTickets(ticketQueue,"open");

            System.out.println("More tickets to add? (enter N for no)");
            String more = getStringInput();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }


    /* Adds tickets in order of priority, or fits a ticket in the right location */
    protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket){

        //Logic: assume the list is either empty or sorted

        if (tickets.size() == 0 ) {//Special case - if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }

        //Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
        //Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < tickets.size() ; x++) {    //use a regular for loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }

        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addLast(newTicket);
    }

    public static int getPositiveIntInput() {
        String userInput;
        int userNumber = -1;

        while (userNumber <= 0) {
            try {
                userInput = scanner.nextLine();
                userNumber = Integer.parseInt(userInput);
                if (userNumber < 0) {
                    System.out.println("Please enter a positive number");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
            }
        }

        return userNumber;
    }

    private static String getStringInput() {
        String input = scanner.nextLine();
        return input;
    }

}

