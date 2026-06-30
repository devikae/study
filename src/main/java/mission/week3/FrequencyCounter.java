package mission.week3;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.*;
public class FrequencyCounter {

    public List<Map.Entry<NumberValue, Integer>> computeStatsFor(List<NumberTicket> ticketList){

        Map<NumberValue, Integer> statistics = new HashMap<>();

        for (int i = NumberValue.MIN_NUM; i <= NumberValue.MAX_NUM; i++) {
            statistics.put(NumberValue.getInstance(i), 0);
        }

        for (NumberTicket ticket : ticketList) {
            for (NumberValue number : ticket.getNumbers()) {
                statistics.put(number, statistics.get(number) + 1);
            }
        }

        List<Map.Entry<NumberValue, Integer>> entryList = new ArrayList<>(statistics.entrySet());

        entryList.sort(
                Map.Entry.<NumberValue, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey())
        );

        return Collections.unmodifiableList(entryList);
    }

    public List<Map.Entry<NumberValue, Integer>> computeStatsStreamSameLogic(List<NumberTicket> ticketList){

        Map<NumberValue, Integer> statistics = new HashMap<>();

        for (int i = NumberValue.MIN_NUM; i <= NumberValue.MAX_NUM; i++) {
            statistics.put(NumberValue.getInstance(i), 0);
        }

        ticketList.stream()
                .flatMap(ticket -> ticket.getNumbers().stream())
                .forEach(numberValue -> {
                    statistics.put(numberValue, statistics.get(numberValue) + 1);
                });

        List<Map.Entry<NumberValue, Integer>> entryList = new ArrayList<>(statistics.entrySet());

        entryList.sort(
                Map.Entry.<NumberValue, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey())
        );

        return Collections.unmodifiableList(entryList);
    }

    public List<Map.Entry<NumberValue, Integer>> computeStatsStreamSorted(List<NumberTicket> ticketList){

        Map<NumberValue, Integer> statistics = new HashMap<>();

        for (int i = NumberValue.MIN_NUM; i <= NumberValue.MAX_NUM; i++) {
            statistics.put(NumberValue.getInstance(i), 0);
        }

        ticketList.stream()
                .flatMap(ticket -> ticket.getNumbers().stream())
                .forEach(numberValue -> {
                    statistics.put(numberValue, statistics.get(numberValue) + 1);
                });

        return statistics.entrySet()
                .stream()
                .sorted(
                        Map.Entry.<NumberValue, Integer>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .toList();
    }


}
