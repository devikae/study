package mission.week3;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {

        NumberGenerator generator = new NumberGenerator();

        List<NumberTicket> tickets = generator.makeTickets(100_000);

        PerformanceComparator performanceComparator = new PerformanceComparator();
        double duration = performanceComparator.getDuration(PerformanceComparator.Status.FOR, tickets);
        System.out.println("for : " + duration);

        duration = performanceComparator.getDuration(PerformanceComparator.Status.STREAM_PLUS_LIST_SORT, tickets);
        System.out.println("Stream(stream + List.sort) : " + duration);

        duration = performanceComparator.getDuration(PerformanceComparator.Status.STREAM_SORT, tickets);
        System.out.println("Stream(stream sorted O) : " + duration);

//        for (Map.Entry<NumberValue, Integer> ticket : sortedTicketsByFor) {
//            System.out.println("로또 번호: " + ticket.getKey().getValue() + " // 출현 빈도: " + ticket.getValue() + "회");
//        }
//        System.out.println("==========================================================================");


    }
}
