package com.conor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by conor on 4/7/16.
 */
public class TicketGUI extends JFrame{
    private JPanel rootPanel;
    private JList ticketListOpen;
    private JButton enterTicketButton;
    private JTextField enterTicketTextField;
    private JButton resolveTicketButton;
    private JTextField resolveTextBox;
    private JButton viewTicketButton;
    private JButton viewResolvedTicketsButton;
    private JButton searchForTicketButton;
    private JTextField searchTextField;
    private JTextField priorityTextField;
    private JTextField reporterTextField;
    private JLabel priorityLabel;

    private DefaultListModel<Ticket> listModel;

    LinkedList<Ticket> ticketQueue = new LinkedList<>();
    LinkedList<Ticket> resolvedTickets = new LinkedList<>();
    Date dateReported = new Date(); //Default constructor creates date with current date/time

    TicketGUI() {
        super("Ticketing program");
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(1400, 500));
        addListeners();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        listModel = new DefaultListModel<Ticket>();
        ticketListOpen.setModel(listModel);


    }

    private void addListeners() {
        enterTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get input from text fields
                String descriptionTicket = enterTicketTextField.getText();
                String reporterTicket = reporterTextField.getText();
                //Exception handling for int field
                try {
                    int priorityTicket = Integer.parseInt(priorityTextField.getText());
                    Ticket t = new Ticket(descriptionTicket, priorityTicket, reporterTicket, dateReported);
                    listModel.addElement(t);
                    priorityLabel.setText("Priority");
                } catch (NumberFormatException ex) {
                    priorityLabel.setText("Please enter a whole number");
                }

                enterTicketTextField.setText("");
                reporterTextField.setText("");
                priorityTextField.setText("");
            }
        });

        viewTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        viewResolvedTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        resolveTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ticket resolve = (Ticket)ticketListOpen.getSelectedValue();
                listModel.removeElement(resolve);
            }
        });
        searchForTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
