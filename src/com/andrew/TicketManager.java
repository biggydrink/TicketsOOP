package com.andrew;

import java.util.*;

public class TicketManager {

    public static Scanner scanner;

    public static void main(String[] args) {

        LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();

        scanner = new Scanner(System.in);

        while(true){

            System.out.println("1. Enter Ticket\n2. Delete Ticket by ID\n3. Delete Ticket by Issue\n4. Search by Name\n5. Display All Tickets\n6. Quit");
            int task = getPositiveIntInput();

            if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);

            } else if (task == 2) {
                //delete a ticket
                deleteTicketByID(ticketQueue);
            } else if (task == 3) {
                LinkedList<Ticket> results = new LinkedList<Ticket>();

                results = searchByName(ticketQueue);

                deleteTicketByID(results);
            } else if (task == 4) {
                LinkedList<Ticket> results = new LinkedList<Ticket>();

                results = searchByName(ticketQueue);
                if (results.isEmpty()) {
                    System.out.println("No results for your search");
                } else {
                    System.out.println("Search results:");
                    printAllTickets(results);
                }


            } else if (task == 5) {
                printAllTickets(ticketQueue);
            } else if ( task == 6 ) {
                //Quit. Future prototype may want to save all tickets to a file
                System.out.println("Quitting program");
                break;
            }
            else {
                //this will happen for 3 or any other selection that is a valid int
                //Default will be print all tickets
                printAllTickets(ticketQueue);
            }
        }

        scanner.close();

    }

    private static String getStringInput() {
        String input = scanner.nextLine();
        return input;
    }

    private static LinkedList<Ticket> searchByName(LinkedList<Ticket> ticketQueue) {

        System.out.println("Enter search query:");
        String query = getStringInput();

        LinkedList<Ticket> searchResults = new LinkedList<Ticket>();

        for (Ticket ticket : ticketQueue) {
            if (ticket.getDescription().contains(query)) {
                searchResults.add(ticket);
            }
        }

        return searchResults;



    }

    protected static void deleteTicketByID(LinkedList<Ticket> ticketQueue) {
        printAllTickets(ticketQueue);   //display list for user

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
                    ticketQueue.remove(ticket);
                    System.out.println(String.format("Ticket %d deleted", deleteID));
                    break; //don't need loop any more.
                }
            }
            if (found == false) {
                System.out.println("Ticket ID not found, no ticket deleted");
                int response = 0;
                while (response < 1 || response > 2) {
                    System.out.println("Enter another ticket ID?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    response = getPositiveIntInput();
                    if (response < 1 || response > 2) {
                        System.out.println("Please enter 1 or 2");
                    }
                }


            }
        }

        printAllTickets(ticketQueue);  //print updated list
    }

    protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println(" ------- All open tickets ----------");

        for (Ticket t : tickets ) {
            System.out.println(t); //Write a toString method in Ticket class
            //println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");

    }

    protected static void addTickets(LinkedList<Ticket> ticketQueue) {
        Scanner sc = new Scanner(System.in);
        boolean moreProblems = true;
        String description, reporter;
        Date dateReported = new Date(); //Default constructor creates date with current date/time
        int priority;

        while (moreProblems){
            System.out.println("Enter problem");
            description = sc.nextLine();
            System.out.println("Who reported this issue?");
            reporter = sc.nextLine();
            System.out.println("Enter priority of " + description);
            priority = Integer.parseInt(sc.nextLine());

            Ticket t = new Ticket(description, priority, reporter, dateReported);
            //ticketQueue.add(t);
            addTicketInPriorityOrder(ticketQueue, t);

            printAllTickets(ticketQueue);

            System.out.println("More tickets to add? (enter N for no)");
            String more = sc.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }

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

    public static double getPositiveDoubleInput() {
        String userInput;
        double userNumber = -1;

        while (userNumber < 0) {
            try {
                userInput = scanner.nextLine();
                userNumber = Double.parseDouble(userInput);
                if (userNumber < 0) {
                    System.out.println("Please enter a positive number");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
            }
        }

        return userNumber;
    }

}

