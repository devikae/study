package mission.week3;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.*;

// Map 초기화, for, Stream만
public class FrequencyCounter {


    public Map<NumberValue, Integer> initializeStatistics() {

        Map<NumberValue, Integer> statistics = new HashMap<>();

        for (int i = NumberValue.MIN_NUM; i <= NumberValue.MAX_NUM; i++) {
            statistics.put(NumberValue.getInstance(i), 0);
        }

        return statistics;

    }

    public void countByFor(List<NumberTicket> ticketList, Map<NumberValue, Integer> statistics){
        validate(ticketList, statistics);

        for (NumberTicket ticket : ticketList) {
            for (NumberValue number : ticket.getNumbers()) {
                statistics.put(number, statistics.get(number) + 1);
            }
        }

    }

    public void countByStream(List<NumberTicket> ticketList, Map<NumberValue, Integer> statistics){
        validate(ticketList, statistics);

        ticketList.stream()
                .flatMap(ticket -> ticket.getNumbers().stream())
                .forEach(numberValue -> {
                    statistics.put(numberValue, statistics.get(numberValue) + 1);
                });

    }

    private void validate(List<NumberTicket> ticketList, Map<NumberValue, Integer> statistics) {
        if (ticketList == null || ticketList.isEmpty()) {
            throw new IllegalArgumentException("ticketList must not be null");
        }

        for (int i = NumberValue.MIN_NUM; i <= NumberValue.MAX_NUM; i++) {
            NumberValue number = NumberValue.getInstance(i);
            if (!statistics.containsKey(number)) {
                throw new IllegalArgumentException("statistics must contain all numbers from 1 to 45");
            }
            if (statistics.get(number) == null) {
                throw new IllegalArgumentException("statistics count must not be null");
            }
            if (statistics.get(number) < 0) {
                throw new IllegalArgumentException("statistics count must not be negative");
            }
        }
    }

}
