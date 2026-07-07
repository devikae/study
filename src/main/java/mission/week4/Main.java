package mission.week4;

import mission.week1.NumberTicket;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int makeTicketCount = 1_000_000;
        List<NumberTicket> tickets =  new ArrayList<>(makeTicketCount);

        TaskFactory taskFactory = new TaskFactory();
        Runnable makeTicketTask = taskFactory.makeTicketTask(tickets, makeTicketCount);

        PerformanceMeasurer performanceMeasurer = new PerformanceMeasurer();
        SingleThreadRunner singleThreadRunner = new SingleThreadRunner();

        double duration = performanceMeasurer.measure(singleThreadRunner, makeTicketTask);

        ResultValidator resultValidator = new ResultValidator();
        boolean isValid = resultValidator.isValid(tickets, makeTicketCount);

        PerformanceResult performanceResult = new PerformanceResult(duration, makeTicketCount, tickets.size(), isValid, singleThreadRunner);

        System.out.println(performanceResult.getResult());


    }
}
