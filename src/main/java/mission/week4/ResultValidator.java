package mission.week4;

import mission.week1.NumberTicket;

import java.util.List;

public class ResultValidator {

    public boolean isValid(List<NumberTicket> tickets, int expectedCount) {
        return tickets.size() == expectedCount;
    }

}
