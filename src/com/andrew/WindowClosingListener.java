package com.andrew;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Calendar;

/**
 * Created by andre_000 on 4/14/2016.
 */
public class WindowClosingListener extends WindowAdapter {

    // Save open and resolved tickets to files
    @Override
    public void windowClosing(WindowEvent e) {
        String openTicketsFileName = "open_tickets.txt";

        Calendar today = Calendar.getInstance();
        String resolvedTicketsFileName = "Resolved_tickets_" +
                (today.get(Calendar.MONTH)+1) + "." + today.get(Calendar.DAY_OF_MONTH) + "." + today.get(Calendar.YEAR) + ".txt";

        TicketManager.saveTickets(TicketManager.openTickets,openTicketsFileName);
        TicketManager.saveTickets(TicketManager.resolvedTickets,resolvedTicketsFileName);

    }

}
