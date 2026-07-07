package mission.week4;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

public class TaskFactory {

    public Runnable makeTicketTask(List<NumberTicket> tickets, int count) {

        if (count < 0) throw new IllegalArgumentException("생성할 티켓은 개수는 0개 이상이여야 합니다.");

        return () -> {
            NumberGenerator generator = new NumberGenerator();

            for (int i = 0; i < count; i++) {
                tickets.add(generator.makeTicket(new HashSet<>()));
            }
        };

    }


}
