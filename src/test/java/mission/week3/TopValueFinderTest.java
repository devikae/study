package mission.week3;

import mission.week1.NumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TopValueFinderTest {

    @Test
    @DisplayName("출현 빈도가 높은 순서대로 정렬")
    void findTopSortsByCountDescending() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), 10);
        statistics.put(NumberValue.getInstance(2), 30);
        statistics.put(NumberValue.getInstance(3), 20);

        List<Map.Entry<NumberValue, Integer>> result = finder.findTop(statistics, 3);

        assertEquals(NumberValue.getInstance(2), result.get(0).getKey());
        assertEquals(NumberValue.getInstance(3), result.get(1).getKey());
        assertEquals(NumberValue.getInstance(1), result.get(2).getKey());
    }

    @Test
    @DisplayName("출현 빈도가 같으면 번호가 작은 순서대로 정렬")
    void findTopSortsByNumberAscendingWhenCountIsSame() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(3), 10);
        statistics.put(NumberValue.getInstance(1), 10);
        statistics.put(NumberValue.getInstance(2), 10);

        List<Map.Entry<NumberValue, Integer>> result = finder.findTop(statistics, 3);

        assertEquals(NumberValue.getInstance(1), result.get(0).getKey());
        assertEquals(NumberValue.getInstance(2), result.get(1).getKey());
        assertEquals(NumberValue.getInstance(3), result.get(2).getKey());
    }

    @Test
    @DisplayName("limit 개수만큼만 반환")
    void findTopReturnsOnlyLimitSize() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), 10);
        statistics.put(NumberValue.getInstance(2), 30);
        statistics.put(NumberValue.getInstance(3), 20);

        List<Map.Entry<NumberValue, Integer>> result = finder.findTop(statistics, 2);

        assertEquals(2, result.size());
        assertEquals(NumberValue.getInstance(2), result.get(0).getKey());
        assertEquals(NumberValue.getInstance(3), result.get(1).getKey());
    }

    @Test
    @DisplayName("limit이 0이면 빈 리스트를 반환한다")
    void findTopReturnsEmptyListWhenLimitIsZero() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), 10);

        List<Map.Entry<NumberValue, Integer>> result = finder.findTop(statistics, 0);

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("limit이 통계 개수보다 크면 전체 통계를 반환한다")
    void findTopReturnsAllWhenLimitIsGreaterThanStatisticsSize() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), 10);
        statistics.put(NumberValue.getInstance(2), 20);

        List<Map.Entry<NumberValue, Integer>> result = finder.findTop(statistics, 10);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("반환된 리스트는 수정할 수 없다")
    void findTopReturnsUnmodifiableList() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), 10);

        List<Map.Entry<NumberValue, Integer>> result = finder.findTop(statistics, 1);

        assertThrows(UnsupportedOperationException.class,
                () -> result.add(Map.entry(NumberValue.getInstance(2), 20)));
    }

    @Test
    @DisplayName("statistics가 null이면 예외가 발생한다")
    void nullStatisticsThrowsException() {
        TopValueFinder finder = new TopValueFinder();

        assertThrows(IllegalArgumentException.class,
                () -> finder.findTop(null, 5));
    }

    @Test
    @DisplayName("statistics가 비어 있으면 예외가 발생한다")
    void emptyStatisticsThrowsException() {
        TopValueFinder finder = new TopValueFinder();

        assertThrows(IllegalArgumentException.class,
                () -> finder.findTop(new HashMap<>(), 5));
    }

    @Test
    @DisplayName("limit이 음수이면 예외가 발생한다")
    void negativeLimitThrowsException() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), 10);

        assertThrows(IllegalArgumentException.class,
                () -> finder.findTop(statistics, -1));
    }

    @Test
    @DisplayName("statistics에 null key가 있으면 예외가 발생한다")
    void nullKeyThrowsException() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(null, 10);

        assertThrows(IllegalArgumentException.class,
                () -> finder.findTop(statistics, 5));
    }

    @Test
    @DisplayName("statistics에 null value가 있으면 예외가 발생한다")
    void nullValueThrowsException() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), null);

        assertThrows(IllegalArgumentException.class,
                () -> finder.findTop(statistics, 5));
    }

    @Test
    @DisplayName("statistics에 음수 value가 있으면 예외가 발생한다")
    void negativeValueThrowsException() {
        TopValueFinder finder = new TopValueFinder();
        Map<NumberValue, Integer> statistics = new HashMap<>();
        statistics.put(NumberValue.getInstance(1), -1);

        assertThrows(IllegalArgumentException.class,
                () -> finder.findTop(statistics, 5));
    }
}
