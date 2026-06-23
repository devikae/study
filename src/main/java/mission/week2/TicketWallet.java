package mission.week2;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicketWallet {

    private final List<NumberTicket> tickets = new ArrayList<>();

    public List<NumberTicket> getTiedTickets() {
        return tickets;
    }

    public void addTicket(NumberTicket ticket) {
        tickets.add(ticket);
    }

}
