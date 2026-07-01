package mission.week3;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.List;
import java.util.Map;


// 측정만
public class PerformanceComparator {

    private double duration;
    private Map<NumberValue, Integer> countedMap;

    enum CountType{
        FOR,
        STREAM
    }

    public void measure(CountType type, List<NumberTicket> tickets) {
        if (type == null) {
            throw new IllegalArgumentException("countType must not be null");
        }

        FrequencyCounter counter = new FrequencyCounter();
        countedMap = counter.initializeStatistics();

        long startTime = System.nanoTime();

        if (type == CountType.FOR) {
            counter.countByFor(tickets, countedMap);
        } else if (type == CountType.STREAM) {
            counter.countByStream(tickets, countedMap);
        }

        long endTime = System.nanoTime();
        duration = (endTime - startTime) / 1_000_000.0;
    }

    public double getDuration() {
        return duration;
    }

    public Map<NumberValue, Integer> getStats() {
        return countedMap;
    }

}
