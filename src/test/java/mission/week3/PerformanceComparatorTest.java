package mission.week3;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceComparatorTest {

    @Test
    @DisplayName("FOR 측정 실행시간")
    void measureForRecordsDuration() {
        PerformanceComparator comparator = new PerformanceComparator();

        comparator.measure(PerformanceComparator.CountType.FOR, createTickets());

        assertTrue(comparator.getDuration() >= 0);
        assertNotNull(comparator.getStats());
    }

    @Test
    @DisplayName("STREAM 측정 실행시간")
    void measureStreamRecordsDuration() {
        PerformanceComparator comparator = new PerformanceComparator();

        comparator.measure(PerformanceComparator.CountType.STREAM, createTickets());

        assertTrue(comparator.getDuration() >= 0);
        assertNotNull(comparator.getStats());
    }

    @Test
    @DisplayName("CountType이 null이면 예외 발생")
    void nullCountTypeThrowsException() {
        PerformanceComparator comparator = new PerformanceComparator();

        assertThrows(IllegalArgumentException.class,
                () -> comparator.measure(null, createTickets()));
    }

    @Test
    @DisplayName("FOR, STREAM은 같은 티켓에 대해 같은 통계와 결과 생성")
    void forAndStreamCreateSameStatistics() {
        List<NumberTicket> tickets = createTickets();
        PerformanceComparator forComparator = new PerformanceComparator();
        PerformanceComparator streamComparator = new PerformanceComparator();

        forComparator.measure(PerformanceComparator.CountType.FOR, tickets);
        streamComparator.measure(PerformanceComparator.CountType.STREAM, tickets);

        assertEquals(forComparator.getStats(), streamComparator.getStats());
    }

    private List<NumberTicket> createTickets() {
        NumberTicket first = new NumberTicket(Set.of(
                NumberValue.getInstance(1),
                NumberValue.getInstance(2),
                NumberValue.getInstance(3),
                NumberValue.getInstance(4),
                NumberValue.getInstance(5),
                NumberValue.getInstance(6)
        ));
        NumberTicket second = new NumberTicket(Set.of(
                NumberValue.getInstance(1),
                NumberValue.getInstance(2),
                NumberValue.getInstance(3),
                NumberValue.getInstance(4),
                NumberValue.getInstance(5),
                NumberValue.getInstance(7)
        ));
        return List.of(first, second);
    }
}
