package mission.week4;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

public class TaskFactory {

    private void validateCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("생성할 티켓의 개수는 0개 이상이어야 합니다.");
        }
    }

    public Runnable makeTicketTask(List<NumberTicket> tickets, int count) {

        validateCount(count);

        return () -> {
            NumberGenerator generator = new NumberGenerator();

            for (int i = 0; i < count; i++) {
                tickets.add(generator.makeTicket(new HashSet<>()));
            }
        };

    }

    public Runnable makeSynchronizedTicketTask(List<NumberTicket> tickets, int count) {

        validateCount(count);

        return () -> {
            NumberGenerator generator = new NumberGenerator();

            for (int i = 0; i < count; i++) {
                NumberTicket ticket = generator.makeTicket(new HashSet<>());

                synchronized (tickets) {
                    tickets.add(ticket);
                }
            }
        };
    }

    public Callable<List<NumberTicket>> makeTicketCallable(int count) {

        validateCount(count);

        return () -> {
            List<NumberTicket> localTickets = new ArrayList<>(count);
            NumberGenerator generator = new NumberGenerator();

            for (int i = 0; i < count; i++) {
                localTickets.add(generator.makeTicket(new HashSet<>()));
            }

            return localTickets;
        };
    }


}
