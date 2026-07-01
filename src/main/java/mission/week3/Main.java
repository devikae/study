package mission.week3;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {

        // 로또 생성
        NumberGenerator generator = new NumberGenerator();
        List<NumberTicket> tickets = generator.makeTickets(1_000_000);

        // for 성능 측정
        PerformanceComparator forResult = new PerformanceComparator();
        forResult.measure(PerformanceComparator.CountType.FOR, tickets);

        // stream 성능 측정
        PerformanceComparator streamResult = new PerformanceComparator();
        streamResult.measure(PerformanceComparator.CountType.STREAM, tickets);

        // 두 map의 결과가 같은지
        boolean sameResult = forResult.getStats().equals(streamResult.getStats());

        if(sameResult) {
            System.out.println("for performance: " + forResult.getDuration() + "ms");
            System.out.println("Stream performance: " + streamResult.getDuration() + "ms");

            // 정렬 및 TOP5
            TopValueFinder valueFinder = new TopValueFinder();
            List<Map.Entry<NumberValue, Integer>> top5 = valueFinder.findTop(streamResult.getStats(), 5);

            // top5 출력
            for(Map.Entry<NumberValue, Integer> entry : top5) {
                System.out.println(entry.getKey().getValue() + "번 : " + entry.getValue() + "회");
            }

        }else {
            throw new IllegalStateException("for and stream counting results are different");
        }




    }
}
