package mission.week4;

import mission.week1.NumberTicket;

import java.util.List;

public class ResultValidator {

    public boolean isValid(List<NumberTicket> tickets, int expectedCount) {
        if (tickets == null) {
            throw new IllegalArgumentException("검증할 티켓 목록은 null일 수 없습니다.");
        }

        return tickets.size() == expectedCount;
    }

}
