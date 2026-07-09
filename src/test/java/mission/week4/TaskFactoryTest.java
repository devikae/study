package mission.week4;

import mission.week1.NumberTicket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskFactoryTest {

    @Test
    @DisplayName("makeTicketTask는 생성 개수만큼 티켓을 추가한다.")
    void makeTicketTaskAddsTicketsByCount() {
        TaskFactory taskFactory = new TaskFactory();
        List<NumberTicket> tickets = new ArrayList<>();

        Runnable task = taskFactory.makeTicketTask(tickets, 10);
        task.run();

        assertEquals(10, tickets.size());
    }

    @Test
    @DisplayName("makeTicketTask는 생성 개수가 음수면 예외가 발생한다.")
    void makeTicketTaskThrowsExceptionWhenCountIsNegative() {
        TaskFactory taskFactory = new TaskFactory();
        List<NumberTicket> tickets = new ArrayList<>();

        assertThrows(IllegalArgumentException.class,
                () -> taskFactory.makeTicketTask(tickets, -1));
    }

    @Test
    @DisplayName("makeTicketTask는 티켓 목록이 null이면 예외가 발생한다.")
    void makeTicketTaskThrowsExceptionWhenTicketsIsNull() {
        TaskFactory taskFactory = new TaskFactory();

        assertThrows(IllegalArgumentException.class,
                () -> taskFactory.makeTicketTask(null, 1));
    }

    @Test
    @DisplayName("makeSynchronizedTicketTask는 생성 개수만큼 티켓을 추가한다.")
    void makeSynchronizedTicketTaskAddsTicketsByCount() {
        TaskFactory taskFactory = new TaskFactory();
        List<NumberTicket> tickets = new ArrayList<>();

        Runnable task = taskFactory.makeSynchronizedTicketTask(tickets, 10);
        task.run();

        assertEquals(10, tickets.size());
    }

    @Test
    @DisplayName("makeSynchronizedTicketTask는 생성 개수가 음수면 예외가 발생한다.")
    void makeSynchronizedTicketTaskThrowsExceptionWhenCountIsNegative() {
        TaskFactory taskFactory = new TaskFactory();
        List<NumberTicket> tickets = new ArrayList<>();

        assertThrows(IllegalArgumentException.class,
                () -> taskFactory.makeSynchronizedTicketTask(tickets, -1));
    }

    @Test
    @DisplayName("makeSynchronizedTicketTask는 티켓 목록이 null이면 예외가 발생한다.")
    void makeSynchronizedTicketTaskThrowsExceptionWhenTicketsIsNull() {
        TaskFactory taskFactory = new TaskFactory();

        assertThrows(IllegalArgumentException.class,
                () -> taskFactory.makeSynchronizedTicketTask(null, 1));
    }

    @Test
    @DisplayName("makeTicketCallable은 생성 개수만큼 티켓 리스트를 반환한다.")
    void makeTicketCallableReturnsTicketsByCount() throws Exception {
        TaskFactory taskFactory = new TaskFactory();

        Callable<List<NumberTicket>> task = taskFactory.makeTicketCallable(10);
        List<NumberTicket> tickets = task.call();

        assertEquals(10, tickets.size());
    }

    @Test
    @DisplayName("makeTicketCallable은 생성 개수가 음수면 예외가 발생한다.")
    void makeTicketCallableThrowsExceptionWhenCountIsNegative() {
        TaskFactory taskFactory = new TaskFactory();

        assertThrows(IllegalArgumentException.class,
                () -> taskFactory.makeTicketCallable(-1));
    }
}
