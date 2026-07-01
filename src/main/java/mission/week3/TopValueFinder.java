package mission.week3;

import mission.week1.NumberValue;

import java.util.*;

// 정렬 + TopList만
public class TopValueFinder {

    public List<Map.Entry<NumberValue, Integer>> findTop(Map<NumberValue, Integer> statistics, int limit) {

        validate(statistics, limit);

        List<Map.Entry<NumberValue, Integer>> entryList = new ArrayList<>(statistics.entrySet());

        entryList.sort(
                Map.Entry.<NumberValue, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey())
        );

        int endIndex = Math.min(limit, entryList.size());

        return Collections.unmodifiableList(new ArrayList<>(entryList.subList(0, endIndex)));
    }

    private void validate(Map<NumberValue, Integer> statistics, int limit) {
        if (statistics == null) {
            throw new IllegalArgumentException("statistics is null");
        }

        if (statistics.isEmpty()) {
            throw new IllegalArgumentException("statistics is empty");
        }

        if (limit < 0) {
            throw new IllegalArgumentException("limit < 0");
        }

        for (Map.Entry<NumberValue, Integer> entry : statistics.entrySet()) {
            if (entry.getKey() == null) {
                throw new IllegalArgumentException("statistics key is null");
            }

            if (entry.getValue() == null) {
                throw new IllegalArgumentException("statistics value is null");
            }

            if (entry.getValue() < 0) {
                throw new IllegalArgumentException("statistics value < 0");
            }
        }
    }
}
