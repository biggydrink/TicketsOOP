package com.andrew;

import com.andrew.Ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;


public class TicketGUI extends JFrame {
    private JList ticketList;
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

    LinkedList<Ticket> ticketQueue = new LinkedList<>();
    LinkedList<Ticket> resolvedTickets = new LinkedList<>();


    public TicketGUI() {
        super("Ticket Manager");
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(700,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listModel = new DefaultListModel<Ticket>();
        ticketList.setModel(listModel);

        addListeners();

        pack();
        setVisible(true);

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
                    ticketQueue.add(t);
                    listModel.addElement(t);

                    // Reset text fields
                    enterTicketIssueTextField.setText("");
                    enterTicketPriorityTextField.setText("");
                    enterTicketCallerTextField.setText("");
                } catch (NumberFormatException nfe) {
                    enterTicketPriorityLabel.setText("Please enter a number from 1-5");
                }

            }


        });

        resolveTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resolve a selected ticket
                // Remove from open ticket list and add to resolved ticket list
            }
        });

        searchTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Search tickets
                // Show them in the list
            }
        });

        viewOpenTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // populate list with all open tickets
            }
        });

        viewResolvedTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }


    /* Adds a ticket to the ticket queue */
    protected void addTicket() {

    }
}
