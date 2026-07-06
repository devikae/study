package mission.week3;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrequencyCounterTest {

    @Test
    @DisplayName("통계 초기 맵은 1부터 45까지, 값은 0으로 초기화한다")
    void initializeStatisticsCreatesAllNumbersWithZeroCount() {
        FrequencyCounter counter = new FrequencyCounter();

        Map<NumberValue, Integer> statistics = counter.initializeStatistics();

        assertEquals(45, statistics.size());
        assertTrue(statistics.containsKey(NumberValue.getInstance(1)));
        assertTrue(statistics.containsKey(NumberValue.getInstance(45)));
        assertTrue(statistics.values().stream().allMatch(count -> count == 0));
    }

    @Test
    @DisplayName("for 방식의 로또 번호 출현 빈도를 카운팅한다")
    void countByForCountsTicketNumbers() {
        FrequencyCounter counter = new FrequencyCounter();
        Map<NumberValue, Integer> statistics = counter.initializeStatistics();

        counter.countByFor(createTickets(), statistics);

        assertEquals(2, statistics.get(NumberValue.getInstance(1)));
        assertEquals(2, statistics.get(NumberValue.getInstance(2)));
        assertEquals(2, statistics.get(NumberValue.getInstance(3)));
        assertEquals(2, statistics.get(NumberValue.getInstance(4)));
        assertEquals(2, statistics.get(NumberValue.getInstance(5)));
        assertEquals(1, statistics.get(NumberValue.getInstance(6)));
        assertEquals(1, statistics.get(NumberValue.getInstance(7)));
        assertEquals(0, statistics.get(NumberValue.getInstance(8)));
    }

    @Test
    @DisplayName("stream 방식 로또 번호의 출현 빈도를 카운팅한다")
    void countByStreamCountsTicketNumbers() {
        FrequencyCounter counter = new FrequencyCounter();
        Map<NumberValue, Integer> statistics = counter.initializeStatistics();

        counter.countByStream(createTickets(), statistics);

        assertEquals(2, statistics.get(NumberValue.getInstance(1)));
        assertEquals(2, statistics.get(NumberValue.getInstance(2)));
        assertEquals(2, statistics.get(NumberValue.getInstance(3)));
        assertEquals(2, statistics.get(NumberValue.getInstance(4)));
        assertEquals(2, statistics.get(NumberValue.getInstance(5)));
        assertEquals(1, statistics.get(NumberValue.getInstance(6)));
        assertEquals(1, statistics.get(NumberValue.getInstance(7)));
        assertEquals(0, statistics.get(NumberValue.getInstance(8)));
    }

    @Test
    @DisplayName("for 방식과 stream 방식은 같은 티켓에 대해 같은 통계 맵을 만든다")
    void countByForAndStreamCreateSameStatistics() {
        FrequencyCounter counter = new FrequencyCounter();
        Map<NumberValue, Integer> forStatistics = counter.initializeStatistics();
        Map<NumberValue, Integer> streamStatistics = counter.initializeStatistics();

        counter.countByFor(createTickets(), forStatistics);
        counter.countByStream(createTickets(), streamStatistics);

        assertEquals(forStatistics, streamStatistics);
    }

    @Test
    @DisplayName("for 초기화되지 않은 통계 맵이면 예외를 던진다")
    void countByForThrowsExceptionWhenStatisticsIsNotInitialized() {
        FrequencyCounter counter = new FrequencyCounter();

        assertThrows(IllegalArgumentException.class,
                () -> counter.countByFor(createTickets(), new HashMap<>()));
    }

    @Test
    @DisplayName("stream 초기화되지 않은 통계 맵이면 예외를 던진다")
    void countByStreamThrowsExceptionWhenStatisticsIsNotInitialized() {
        FrequencyCounter counter = new FrequencyCounter();

        assertThrows(IllegalArgumentException.class,
                () -> counter.countByStream(createTickets(), new HashMap<>()));
    }

    @Test
    @DisplayName("for 방식은 음수 카운트가 있는 통계 맵이면 예외를 던진다")
    void countByForThrowsExceptionWhenStatisticsHasNegativeCount() {
        FrequencyCounter counter = new FrequencyCounter();
        Map<NumberValue, Integer> statistics = counter.initializeStatistics();
        statistics.put(NumberValue.getInstance(1), -1);

        assertThrows(IllegalArgumentException.class,
                () -> counter.countByFor(createTickets(), statistics));
    }

    @Test
    @DisplayName("stream 방식은 음수 카운트가 있는 통계 맵이면 예외를 던진다")
    void countByStreamThrowsExceptionWhenStatisticsHasNegativeCount() {
        FrequencyCounter counter = new FrequencyCounter();
        Map<NumberValue, Integer> statistics = counter.initializeStatistics();
        statistics.put(NumberValue.getInstance(1), -1);

        assertThrows(IllegalArgumentException.class,
                () -> counter.countByStream(createTickets(), statistics));
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
