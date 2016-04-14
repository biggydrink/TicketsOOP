package com.andrew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class TicketGUI extends JFrame {
    private JList<Ticket> ticketList;
    private JButton enterTicketButton;
    private JPanel rootPanel;
    public JTextField enterTicketIssueTextField;
    private JButton resolveTicketButton;
    public JTextField resolveTicketResolutionTextField;
    private JButton searchTicketButton;
    public JTextField searchTicketTextField;
    private JButton viewOpenTicketsButton;
    private JButton viewResolvedTicketsButton;
    private JLabel enterTicketIssueLabel;
    public JTextField enterTicketPriorityTextField;
    public JTextField enterTicketCallerTextField;
    private JLabel enterTicketPriorityLabel;
    private JLabel enterTicketCallerLabel;
    private JLabel resolveTicketResolutionLabel;

    private DefaultListModel<Ticket> listModel;


    public TicketGUI() {
        super("Ticket Manager");
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1400,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listModel = new DefaultListModel<Ticket>();
        ticketList.setModel(listModel);
        ticketList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Can only select one item at a time

        addListeners();
        addWindowListener(new WindowClosingListener());

        pack();
        setVisible(true);

        populateModel(TicketManager.openTickets);

    }

    public void addListeners() {

        enterTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = enterTicketIssueTextField.getText();
                String caller = enterTicketCallerTextField.getText();
                int priority;

                try {
                    priority = Integer.parseInt(enterTicketPriorityTextField.getText());
                    enterTicketPriorityLabel.setText("Priority: ");
                    Ticket t = new Ticket(description, priority, caller);
                    TicketManager.openTickets.add(t);

                    // Reset text fields
                    enterTicketIssueTextField.setText("");
                    enterTicketPriorityTextField.setText("");
                    enterTicketCallerTextField.setText("");
                } catch (NumberFormatException nfe) {
                    enterTicketPriorityLabel.setText("Please enter a number from 1-5");
                }

                populateModel(TicketManager.openTickets);

            }


        });

        resolveTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resolution = resolveTicketResolutionTextField.getText();
                Ticket newResolvedTicket = ticketList.getSelectedValue();

                // Set resolution for selected ticket
                newResolvedTicket.setResolution(resolution);

                // Remove from open ticket list and add to resolved ticket list
                TicketManager.openTickets.remove(newResolvedTicket);
                TicketManager.resolvedTickets.add(newResolvedTicket);

                // Show open tickets only in list (aka remove new resolved ticket from current view)
                populateModel(TicketManager.openTickets);

            }
        });

        searchTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Search tickets
                String query = searchTicketTextField.getText();
                LinkedList<Ticket> searchResults = TicketManager.searchByName(TicketManager.openTickets,query);

                // Show them in the list
                populateModel(searchResults);
            }
        });

        viewOpenTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateModel(TicketManager.openTickets);
            }
        });

        viewResolvedTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateModel(TicketManager.resolvedTickets);
            }
        });

    }


    protected void populateModel(LinkedList<Ticket> ticketList) {
        listModel.clear();
        for (Ticket ticket : ticketList) {
            listModel.addElement(ticket);
        }
    }
}
