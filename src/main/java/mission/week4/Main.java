package mission.week4;

import mission.week1.NumberTicket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {

        final int MAKE_TICKET_COUNT = 1_000_000;

        List<NumberTicket> tickets =  new ArrayList<>(MAKE_TICKET_COUNT);

        TaskFactory taskFactory = new TaskFactory();
        List<Runnable> makeTicketTask = new ArrayList<>();
        makeTicketTask.add(taskFactory.makeTicketTask(tickets, MAKE_TICKET_COUNT));

        PerformanceMeasurer performanceMeasurer = new PerformanceMeasurer();
        SingleThreadRunner singleThreadRunner = new SingleThreadRunner();

        double duration = performanceMeasurer.measure(() -> {
            singleThreadRunner.execute(makeTicketTask);
        });

        ResultValidator resultValidator = new ResultValidator();
        boolean isValid = resultValidator.isValid(tickets, MAKE_TICKET_COUNT);

        PerformanceResult performanceResult = new PerformanceResult(
                "single thread",
                duration,
                MAKE_TICKET_COUNT,
                tickets.size(),
                isValid,
                singleThreadRunner
        );

        System.out.println(performanceResult.getResult());


        int threadCount = 4;
        int countPerThread = MAKE_TICKET_COUNT / threadCount;

        List<NumberTicket> sharedTickets = new ArrayList<>(MAKE_TICKET_COUNT);
        List<Runnable> sharedTicketTasks = new ArrayList<>(threadCount);

        for (int i = 0; i < threadCount; i++) {
            sharedTicketTasks.add(taskFactory.makeTicketTask(sharedTickets, countPerThread));
        }

        MultiThreadRunner multiThreadRunner = new MultiThreadRunner();

        double sharedDuration = performanceMeasurer.measure(() -> {
            multiThreadRunner.execute(sharedTicketTasks);
        });

        boolean sharedIsValid = resultValidator.isValid(sharedTickets, MAKE_TICKET_COUNT);

        PerformanceResult sharedResult = new PerformanceResult(
                "multi thread not control concurrency",
                sharedDuration,
                MAKE_TICKET_COUNT,
                sharedTickets.size(),
                sharedIsValid,
                multiThreadRunner
        );

        System.out.println(sharedResult.getResult());


        // task 에서 add() 할 때 synchronized로 블럭 내에서 add()
        List<NumberTicket> synchronizedAddTickets = new ArrayList<>(MAKE_TICKET_COUNT);
        List<Runnable> synchronizedTicketTasks = new ArrayList<>(threadCount);

        for (int i = 0; i < threadCount; i++) {
            synchronizedTicketTasks.add(taskFactory.makeSynchronizedTicketTask(synchronizedAddTickets, countPerThread));
        }

        double synchronizedAddDuration = performanceMeasurer.measure(() -> {
            multiThreadRunner.execute(synchronizedTicketTasks);
        });

        boolean synchronizedAddIsValid = resultValidator.isValid(synchronizedAddTickets, MAKE_TICKET_COUNT);

        PerformanceResult synchronizedAddResult = new PerformanceResult(
                "synchronized block control concurrency",
                synchronizedAddDuration,
                MAKE_TICKET_COUNT,
                synchronizedAddTickets.size(),
                synchronizedAddIsValid,
                multiThreadRunner
        );

        System.out.println(synchronizedAddResult.getResult());



        // copyOnWrite 방식, 쓰기에 부적절
//        List<NumberTicket> copyOnWriteTickets = new CopyOnWriteArrayList<>();
//        List<Runnable> copyOnWriteTasks = new ArrayList<>(threadCount);
//
//        for (int i = 0; i < threadCount; i++) {
//            copyOnWriteTasks.add(taskFactory.makeTicketTask(copyOnWriteTickets, countPerThread));
//        }
//
//        double copyOnWriteDuration = performanceMeasurer.measure(multiThreadRunner, copyOnWriteTasks);
//
//        boolean copyOnWriteIsValid = resultValidator.isValid(copyOnWriteTickets, MAKE_TICKET_COUNT);
//
//        PerformanceResult copyOnWriteResult = new PerformanceResult(
//                "copy on write list control concurrency",
//                copyOnWriteDuration,
//                MAKE_TICKET_COUNT,
//                copyOnWriteTickets.size(),
//                copyOnWriteIsValid,
//                multiThreadRunner
//        );
//
//        System.out.println(copyOnWriteResult.getResult());



        // 회피 방식
        List<NumberTicket> avoidTickets = new ArrayList<>(MAKE_TICKET_COUNT);
        List<Callable<List<NumberTicket>>> avoidTicketTasks = new ArrayList<>(threadCount);

        for (int i = 0; i < threadCount; i++) {
            avoidTicketTasks.add(taskFactory.makeTicketCallable(countPerThread));
        }

        double avoidDuration = performanceMeasurer.measure(() -> {
            avoidTickets.addAll(
                    multiThreadRunner.executeAndCollect(avoidTicketTasks)
            );
        });

        boolean avoidIsValid = resultValidator.isValid(avoidTickets, MAKE_TICKET_COUNT);

        PerformanceResult avoidResult = new PerformanceResult(
                "multi thread avoid",
                avoidDuration,
                MAKE_TICKET_COUNT,
                avoidTickets.size(),
                avoidIsValid,
                multiThreadRunner
        );

        System.out.println(avoidResult.getResult());











    }
}
