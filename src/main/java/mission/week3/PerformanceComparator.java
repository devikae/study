package mission.week3;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PerformanceComparator {

    long startTime = 0;
    long endTime = 0;
    double duration = 0;

    public enum Status {
        FOR,
        STREAM_PLUS_LIST_SORT,
        STREAM_SORT
    }

    public double getDuration(Status status, List<NumberTicket> tickets) {
        FrequencyCounter stats = new FrequencyCounter();

        List<Map.Entry<NumberValue, Integer>> ticketList = new ArrayList<>();

        if(status == Status.FOR){
            startTime = System.nanoTime();
            ticketList = stats.computeStatsFor(tickets);
            endTime = System.nanoTime();

        }else if(status == Status.STREAM_PLUS_LIST_SORT){
            startTime = System.nanoTime();
            ticketList = stats.computeStatsStreamSameLogic(tickets);
            endTime = System.nanoTime();

        }else if(status == Status.STREAM_SORT){
            startTime = System.nanoTime();
            ticketList = stats.computeStatsStreamSorted(tickets);
            endTime = System.nanoTime();

        }

        duration = (endTime - startTime)/1_000_000.0;

        return duration;
    }

    // TODO
    // 요구사항 3. 반복문으로 값별 등장 횟수를 계산합니다.
    // 같은 tickets를 넣는다고 해서 결과가 같다는 보장은 “로직이 맞을 때만” 성립
    // tickets가 동일한지 확인

}
